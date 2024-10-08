package Model;

/**
 * This class models an Account.
 */
public class Account {
    /**
     * An id for this Account which will be automatically generated by the database.
     */
    public int account_id;
    /**
     * A username for this Account (must be unique and not blank)
     */
    public String username;
    /**
     * A password for this account (must be over 4 characters)
     */
    public String password;
    
    public Account(){
    }
    
    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public Account(int account_id, String username, String password) {
        this.account_id = account_id;
        this.username = username;
        this.password = password;
    }
    
    public int getAccount_id() {
        return account_id;
    }
    
    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Overriding the default equals() method adds functionality to tell when two objects are identical, allowing
     * Assert.assertEquals and List.contains to function.
     * @param o the other object.
     * @return true if o is equal to this object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return account_id == account.account_id && username.equals(account.username) && password.equals(account.password);
    }
    /**
     * Overriding the default toString() method allows for easy debugging.
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
