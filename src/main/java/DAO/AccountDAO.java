package DAO;

import Model.Account;
public interface AccountDAO {
    public Account addAccount(Account newAccount);
    public Account login(Account account);
    
}
