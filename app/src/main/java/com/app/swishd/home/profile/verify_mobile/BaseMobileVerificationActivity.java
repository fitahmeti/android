package com.app.swishd.home.profile.verify_mobile;

import android.support.annotation.NonNull;
import android.util.Log;

import com.app.swishd.baseutil.BaseFragmentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public abstract class BaseMobileVerificationActivity extends BaseFragmentActivity {

    private static String mVerificationId;
    private static PhoneAuthProvider.ForceResendingToken mResendToken;
    private static String mobile;
    private static boolean codeSendInProgress;

    private static String countryCode;

    public abstract void onVerificationCodeSent();

    public abstract void onSuccessVerification();

    public void sendVerificationCode(String countryCode, String mobile) {
        showProgressDialog();
        this.countryCode = countryCode;
        this.mobile = mobile;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                getFullMobileNumber(),        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        codeSendInProgress = true;
    }

    public void resendVerificationCode() {
        showProgressDialog();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                getFullMobileNumber(),        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                mResendToken);             // ForceResendingToken from callbacks
        codeSendInProgress = true;
    }

    public void verifyPhoneNumberWithCode(String code) {
        showProgressDialog();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
//                            FirebaseUser user = task.getResult().getUser();
                            hideProgressDialog();
                            onSuccessVerification();
                        } else {
                            hideProgressDialog();
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                showSnackBar("Invalid code.");
                            } else {
                                showSnackBar(task.getException().getMessage());
                            }
                        }
                    }
                });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            hideProgressDialog();
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verificaiton without
            //     user action.
            Log.d("TAG", "onVerificationCompleted:" + credential);
            codeSendInProgress = false;
            onSuccessVerification();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            hideProgressDialog();
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("TAG", "onVerificationFailed", e);
            codeSendInProgress = false;

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                showSnackBar("Invalid phone number.");
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                showSnackBar("Verification facility paused for limited time, Caused by Limited Quota.");
            } else {
                showSnackBar("Verification Failed");
            }
        }

        @Override
        public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
            hideProgressDialog();
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("TAG", "onCodeSent:" + verificationId);

            codeSendInProgress = false;

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            mResendToken = token;
            onVerificationCodeSent();
        }
    };

    public static String getMobileNumber() {
        return mobile;
    }

    public static String getCountryCode() {
        return countryCode;
    }

    public static String getFullMobileNumber() {
        return countryCode + mobile;
    }
}
