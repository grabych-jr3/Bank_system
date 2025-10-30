package bank.management.system.model;


public class Account {
    private int accountId;
    private  int userId;
    private double balance;
    private String account = generateAccount();
    private String currency;
    private String createdAt;

    public Account(){}
    public Account(int aId, int uId, double b, String c){
        accountId = aId;
        userId = uId;
        balance = b;
        currency = c;
    }
    public Account(String c){
        currency = c;
    }

    public int getAccountId(){ return accountId; }
    public int getUserId(){ return userId; }
    public double getBalance(){ return this.balance; }
    public String getAccount(){ return account; }
    public String getCurrency(){ return currency; }
    public String getCreatedAt(){ return createdAt; }

    public void setAccountId(int aId){ accountId = aId; }
    public void setUserId(int uId){ userId = uId; }
    public void setBalance(double b){ balance = b; }
    public void setAccount(String acc){ account = acc; }
    public void setCurrency(String cur){ currency = cur; }
    public void setCreatedAt(String cAt){ createdAt = cAt; }

    private String generateAccount(){
       String acc = "PL";
       for(int i = 0; i < 26; i++){
           acc += (int)(Math.random() * 10);
       }
       return acc;
    }

    @Override
    public String toString(){
        return accountId+" | "+userId+" | "+balance+" | "+currency+" | "+account+" | "+createdAt+"\n";
    }
}
