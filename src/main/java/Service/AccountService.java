package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

/**
 * This class links the account DAO and Controller.
 */
public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account a){
        return accountDAO.insertAccount(a);
    }

    public Account login(Account a){
        return accountDAO.login(a);
    }
}
