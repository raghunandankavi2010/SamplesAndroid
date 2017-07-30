package assignment.com.raghu.androdiassignment.dagger.modules;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by raghu on 29/7/17.
 */

@Scope
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PerActivity {

}