package DAO;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
public class AccountDAOImp implements AccountDAO {
    Connection connection;
    public AccountDAOImp()
    {
        this.connection = ConnectionUtil.getConnection();
    }

    @Override
    public Account addAccount(Account newAccount)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * from account where username = ?");
            statement.setString(1, newAccount.getUsername());
            ResultSet rs = statement.executeQuery();
            if(!rs.next())
            {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO ACCOUNT VALUES (default, ?, ?)");
                ps.setString(1, newAccount.getUsername());
                ps.setString(2, newAccount.getPassword());
                int updatedRows = ps.executeUpdate();
                if(updatedRows > 0)
                {
                    statement = connection.prepareStatement("SELECT * FROM account where username = ? AND password = ?");
                    statement.setString(1, newAccount.getUsername());
                    statement.setString(2, newAccount.getPassword());
                    rs = statement.executeQuery();
                    rs.next();
                    return (new Account(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account login(Account account)
    {
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT * from account where username = ? AND password = ?");
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}

