import view.AuthView;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuthView authView = new AuthView();
            authView.setVisible(true);

            // A abertura da tela principal ocorre após a autenticação
        });
    }
}
