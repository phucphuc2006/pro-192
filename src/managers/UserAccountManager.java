package managers;

import models.UserAccount;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserAccountManager {
    private List<UserAccount> accounts;
    private final String FILE_PATH = "data/users.txt";

    public UserAccountManager() {
        this.accounts = new ArrayList<>();
        loadFromFile();
    }

    public void addAccount(UserAccount account) {
        accounts.add(account);
        saveToFile();
    }

    public UserAccount login(String username, String password) {
        for (UserAccount acc : accounts) {
            if (acc.getUsername().equals(username) && acc.getPassword().equals(password)) {
                return acc;
            }
        }
        return null;
    }

    public List<UserAccount> getAllAccounts() {
        return accounts;
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    accounts.add(new UserAccount(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (UserAccount acc : accounts) {
                bw.write(String.format("%s,%s,%s,%s",
                        acc.getUserID(), acc.getUsername(), acc.getPassword(), acc.getRole()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }
}
