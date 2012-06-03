package mdrive.page.go;

import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.PatternValidator;

import java.util.Map;

/**
 * User: andrey.osipov
 * Date: 2/15/12
 * Time: 6:02 PM
 */
public class MyPatternValidator extends PatternValidator {

    MyPatternValidator(String pattern) {
        super(pattern);
    }

    public MyPatternValidator(MetaPattern pattern) {
        super(pattern);
    }

    @Override
    protected Map<String, Object> variablesMap(IValidatable<String> validatable) {
        final Map<String, Object> map = super.variablesMap(validatable);
        map.put("msg1", "test message");
        return map;
    }
}