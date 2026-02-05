package loginmodule;

public interface ValidatorInterface {
    boolean passesSQLInjection();
    boolean passesPWPolicy();
    boolean passesMultiFactorAuthentication();
    boolean authenticatesUser();
}
