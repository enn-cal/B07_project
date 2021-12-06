package com.example.appsimulator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    MainActivity view;

    @Mock
    ModelLogin model;

    @Test
    public void testPresenterloginSuccess()
    {
        when(view.getEmail()).thenReturn("r@gmail.com");
        when(view.getPassword()).thenReturn("r");
        when(view.getUserType()).thenReturn("Customer");
        PresenterLogin p = new PresenterLogin(view);
        p.setModel(model);
        p.start("c@gmail.com", "c", "Customer");
        p.loginSuccess("1302843028");
        verify(view).loginSuccess("1302843028");
    }
    @Test
    public void testPresenterloginFailed()
    {
        when(view.getEmail()).thenReturn("loginfail@gmail.com");
        when(view.getPassword()).thenReturn("loginfail");
        when(view.getUserType()).thenReturn("Store Owner");
        PresenterLogin p = new PresenterLogin(view);
        p.setModel(model);
        p.start("loginfail@gmail.com", "loginfail", "Store Owner");
        p.loginFailed("Not a registered user, please register.");
        verify(view).loginFailed("Not a registered user, please register.");
    }
}