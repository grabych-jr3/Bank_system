package bank.management.system.model;

public class User {
    private int id;
    private String login;
    private String password;
    private String fullName;
    private String createdAt;

    public User(){}

    public User(String login, String password, String fullName){
        this.login = login;
        this.password = password;
        this.fullName = fullName;
    }

    public User(int id, String login, String password, String fullName, String createdAt){
        this.id = id;
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) { this.password = password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString(){
        return id+" | "+login+" | "+password+" | "+fullName+" | "+createdAt+"\n";
    }
}
