package dubbo.wk.utils.config;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by wgp on 2018/6/22.
 */
public class PropertyPlaceholderHelper {
    private static final Map<String, String> wellKnownSimplePrefixes = new HashMap<>(16);

    public static PropertyPlaceholderHelper defaultHolder = new PropertyPlaceholderHelper("$!{", "}");

    private final String placeholderPrefix;
    private final String placeholderSuffix;
    private final String simplePrefix;
    private final String valueSeparator;
    private final Boolean ignoreUnresolvablePlaceholders;


    public PropertyPlaceholderHelper(String placeholderPrefix,String placeholderSuffix){
        this(placeholderPrefix,placeholderSuffix,null,null);
    }

    public PropertyPlaceholderHelper(String placeholderPrefix, String placeholderSuffix,
                                     String valueSeparator, Boolean ignoreUnresolvablePlaceholders) {
        Assert.notNull(placeholderPrefix,"'placeholderPrefix' must not be null");
        Assert.notNull(placeholderSuffix,"'placeholderSuffix' must not be null");
        this.placeholderPrefix  = placeholderPrefix;
        this.placeholderSuffix = placeholderSuffix;

        //  前缀 {}
        String simplePrefixForSuffix = wellKnownSimplePrefixes.get(this.placeholderSuffix);
        if(simplePrefixForSuffix != null && this.placeholderPrefix.endsWith(simplePrefixForSuffix)){
            this.simplePrefix = simplePrefixForSuffix;
        }else{
            this.simplePrefix = this.placeholderPrefix;
        }

        this.valueSeparator = valueSeparator;
        this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;

    }

    public String replacePlaceholders(String value, final Properties properties){
        Assert.notNull(properties,"'properties' must not be null");
        return replacePlaceholder(value,new PlaceholderResolver(){
            @Override
            public String resolvePlaceholder(String placeholderName) {
                return properties.getProperty(placeholderName);
            }
        });
    }

    private String replacePlaceholder(String value, PlaceholderResolver placeholderResolver) {
        Assert.notNull(value,"'value must be not null'");
       return parseStringValue(value,placeholderResolver,new HashSet<String>());
    }

    protected String parseStringValue(
        String strVal,PlaceholderResolver placeholderResolver,Set<String> visitedPlaceholders){
        //创建包含格式化字符串的一个buff类
        StringBuffer result = new StringBuffer(strVal);
        //计算出前缀 在字符串的起始索引处。
        int startIndex = strVal.indexOf(this.placeholderPrefix);
        while (startIndex != -1) {
                        int endIndex = findPlaceholderEndIndex(result, startIndex);
                         if (endIndex != -1) {
                                String placeholder = result.substring(startIndex + this.placeholderPrefix.length(), endIndex);
                                String originalPlaceholder = placeholder;
                                 if (!visitedPlaceholders.add(originalPlaceholder)) {
                                         throw new IllegalArgumentException(
                                                         "Circular placeholder reference '" + originalPlaceholder + "' in property definitions");
                                     }
                                // Recursive invocation, parsing placeholders contained in the placeholder key.
                                 placeholder = parseStringValue(placeholder, placeholderResolver, visitedPlaceholders);
                                 // Now obtain the value for the fully resolved key...
                                String propVal = placeholderResolver.resolvePlaceholder(placeholder);
                                if (propVal == null && this.valueSeparator != null) {
                                         int separatorIndex = placeholder.indexOf(this.valueSeparator);
                                       if (separatorIndex != -1) {
                                                String actualPlaceholder = placeholder.substring(0, separatorIndex);
                                                 String defaultValue = placeholder.substring(separatorIndex + this.valueSeparator.length());
                                                 propVal = placeholderResolver.resolvePlaceholder(actualPlaceholder);
                                                if (propVal == null) {
                                                         propVal = defaultValue;
                                                    }
                                            }
                                    }
                               if (propVal != null) {
                                        // Recursive invocation, parsing placeholders contained in the
                                        // previously resolved placeholder value.
                                        propVal = parseStringValue(propVal, placeholderResolver, visitedPlaceholders);
                                         result.replace(startIndex, endIndex + this.placeholderSuffix.length(), propVal);
                                         startIndex = result.indexOf(this.placeholderPrefix, startIndex + propVal.length());
                                    } else if (this.ignoreUnresolvablePlaceholders) {
                                      // Proceed with unprocessed value.
                                       startIndex = result.indexOf(this.placeholderPrefix, endIndex + this.placeholderSuffix.length());
                                    } else {
                                        throw new IllegalArgumentException("Could not resolve placeholder '" +
                                                        placeholder + "'" + " in string value \"" + strVal + "\"");
                                    }
                                 visitedPlaceholders.remove(originalPlaceholder);
                            } else {
                                 startIndex = -1;
                             }
                     }

                return result.toString();




    }


    private int findPlaceholderEndIndex(CharSequence buf,int startIndex){
            // 排除第一个前缀以前的长度
            int index = startIndex + this.placeholderPrefix.length();
            int withinNestedPlaceholder = 0;
            while(index < buf.length()){
                if(StringUtils.substringMatch(buf,index,this.placeholderSuffix)){
                    if(withinNestedPlaceholder >0){
                        withinNestedPlaceholder--;
                        index = index + this.placeholderSuffix.length();
                    }else{
                        return index;
                    }
                }else if(StringUtils.substringMatch(buf,index,this.simplePrefix)){
                    withinNestedPlaceholder++;
                    index = index + this.simplePrefix.length();
                }else{
                    index++;
                }
            }
            return -1;
    }


    public static interface PlaceholderResolver{
        String resolvePlaceholder(String placeholderName);
    }

    public static void main(String[] args) {
          String  a = "dfd{}dfdf";
          int startIndex = a.indexOf("{}");
        System.out.println(startIndex);
        StringBuffer buffer = new StringBuffer(a);
        int len = "{}".length() + startIndex;
        System.out.println(len);
    }
}
