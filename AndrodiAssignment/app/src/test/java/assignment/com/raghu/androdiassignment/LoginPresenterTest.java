package assignment.com.raghu.androdiassignment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import assignment.com.raghu.androdiassignment.listeners.OnLoginValidateListener;
import assignment.com.raghu.androdiassignment.model.LoginModel;
import assignment.com.raghu.androdiassignment.presenters.LoginPresenter;
import assignment.com.raghu.androdiassignment.presenters.LoginPresenterContract;
import assignment.com.raghu.androdiassignment.utils.Utility;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by raghu on 30/7/17.
 */

public class LoginPresenterTest {


    @Mock
    LoginPresenterContract.View view;

    @Mock
    LoginModel loginModel;

    @Captor
    private ArgumentCaptor<OnLoginValidateListener> onLoginValidateListenerArgumentCaptor;

    private LoginPresenter presenter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        presenter = new LoginPresenter(loginModel);
        presenter.onViewCreated(view);
    }

    @Test
    public void Success() {

        presenter.validate("9986929644","Raghu@123");

        verify(view).showProgress(true);
        verify(loginModel).validateCredentials(eq("9986929644"),eq("Raghu@123"),onLoginValidateListenerArgumentCaptor.capture());

        onLoginValidateListenerArgumentCaptor.getValue().onLoginSuccess();

        verify(view).showProgress(false);
        verify(view).loginSuccess();


    }

    @Test
    public void Failure() {

        presenter.validate("99869296441","Raghu@123");

        verify(view).showProgress(true);
        verify(loginModel).validateCredentials(eq("99869296441"),eq("Raghu@123"),onLoginValidateListenerArgumentCaptor.capture());

        onLoginValidateListenerArgumentCaptor.getValue().onLoginFailed();

        verify(view).showProgress(false);
        verify(view).loginFailed();


    }


    @Test
    public void CheckFieldPhoneEmpty() {

        presenter.validate("","Raghu@123");

        verify(view).showProgress(true);
        verify(loginModel).validateCredentials(eq(""),eq("Raghu@123"),onLoginValidateListenerArgumentCaptor.capture());

        onLoginValidateListenerArgumentCaptor.getValue().onLoginPhoneError("Phone number must be a valid 10 digit number");

        verify(view).showProgress(false);
        verify(view).phoneError();
        verify(view).showSnackBar("Phone number must be a valid 10 digit number");


    }

    @Test
    public void CheckFieldPasswordEmpty() {

        presenter.validate("9986929644","");

        verify(view).showProgress(true);
        verify(loginModel).validateCredentials(eq("9986929644"),eq(""),onLoginValidateListenerArgumentCaptor.capture());

        onLoginValidateListenerArgumentCaptor.getValue().onLoginPasswordError("Entered password is not valid");

        verify(view).showProgress(false);
        verify(view).passwordError();
        verify(view).showSnackBar("Entered password is not valid");


    }

    @Test
    public void InvalidPassword() {

        presenter.validate("9986929644","12252412");

        verify(view).showProgress(true);
        verify(loginModel).validateCredentials(eq("9986929644"),eq("12252412"),onLoginValidateListenerArgumentCaptor.capture());

        onLoginValidateListenerArgumentCaptor.getValue().onLoginPasswordError("Entered password is not valid");

        verify(view).showProgress(false);
        verify(view).passwordError();
        verify(view).showSnackBar("Entered password is not valid");


    }

    @Test
    public void InvalidPhoneNumber() {

        presenter.validate("998692964444","Raghu@123");

        verify(view).showProgress(true);
        verify(loginModel).validateCredentials(eq("998692964444"),eq("Raghu@123"),onLoginValidateListenerArgumentCaptor.capture());

        onLoginValidateListenerArgumentCaptor.getValue().onLoginPhoneError("Phone number must be a valid 10 digit number");

        verify(view).showProgress(false);
        verify(view).phoneError();
        verify(view).showSnackBar("Phone number must be a valid 10 digit number");


    }

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertThat(Utility.validateEmailRegex("name@email.com"), is(true));
    }

    @Test
    public void password_validator_fail() {
        assertThat(Utility.validatePasswordRegex("123243"), is(true));
    }

    @Test
    public void password_validator_pass() {
        assertThat(Utility.validatePasswordRegex("Raghu@123"), is(true));
    }
}
