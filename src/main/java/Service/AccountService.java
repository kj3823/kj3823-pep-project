package Service;

import DAO.AccountDAOImp;
import Model.Account;

public class AccountService
{
    private final AccountDAOImp accountDAOImp;

    public AccountService() {
        this.accountDAOImp = new AccountDAOImp();
    }

    public Account Login(Account account)
    {
        return this.accountDAOImp.login(account);
    }

    public Account addAccount(Account newAccount)
    {
        return this.accountDAOImp.addAccount(newAccount);
    }
}
