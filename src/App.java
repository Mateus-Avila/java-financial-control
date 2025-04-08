import view.AuthView;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuthView authView = new AuthView();
            authView.setVisible(true);

            // Após autenticação bem-sucedida:
            // MainView mainView = new MainView();
            // mainView.setVisible(true);
        });
    }
}