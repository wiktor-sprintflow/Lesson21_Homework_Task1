import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/convertWeight")
public class ConvertWeightServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        List<String> formData = new ArrayList<>();
        formData.add(request.getParameter("kilograms"));
        formData.add(request.getParameter("grams"));
        formData.add(request.getParameter("milligrams"));

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        try {
            if (countNonEmptyInputs(formData) != 1) {
                writer.println("<h3>Należy wypełnić tylko jedno pole</h3>");
            } else {
                String kilogramsFromParameter = request.getParameter("kilograms");
                String gramsFromParameter = request.getParameter("grams");
                String milligramsFromParameter = request.getParameter("milligrams");

                if (!kilogramsFromParameter.equals("")) {
                    double kilograms = Double.parseDouble(kilogramsFromParameter);
                    showConvertedValues(writer, kilograms, kilograms * 1000, kilograms * 1000000);
                } else if (!gramsFromParameter.equals("")) {
                    double grams = Double.parseDouble(gramsFromParameter);
                    showConvertedValues(writer, grams / 1000, grams, grams * 1000);
                } else {
                    double milligrams = Double.parseDouble(milligramsFromParameter);
                    showConvertedValues(writer, milligrams / 1000000, milligrams / 1000, milligrams);
                }
            }
        } catch (NumberFormatException e) {
            writer.println("<h3>Wprowadzane dane muszą być liczbowe.</h3>");
        }
    }

    private int countNonEmptyInputs(List<String> list) {
        int counter = 0;
        for (String element : list) {
            if (!element.equals("")) {
                counter++;
            }
        }
        return counter;
    }

    private void showConvertedValues(PrintWriter writer, double kilograms, double grams, double milligrams) {
        writer.println("<h2>Podana wartość w przeliczeniu na:</h2>");
        writer.println("<h3>kilogramy: " + kilograms + "</h3>");
        writer.println("<h3>gramy: " + grams + "</h3>");
        writer.println("<h3>miligramy: " + milligrams + " </h3>");
    }
}
