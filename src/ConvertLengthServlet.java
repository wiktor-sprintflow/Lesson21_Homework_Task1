import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/convertLength")
public class ConvertLengthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        List<String> formData = new ArrayList<>();
        formData.add(request.getParameter("meters"));
        formData.add(request.getParameter("centimeters"));
        formData.add(request.getParameter("millimeters"));

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        try {
            if (countNonEmptyInputs(formData) != 1) {
                writer.println("<h3>Należy wypełnić tylko jedno pole</h3>");
            } else {

                String metersFromRequest = request.getParameter("meters");
                String centimetersFromRequest = request.getParameter("centimeters");
                String millimetersFromRequest = request.getParameter("millimeters");

                if (!metersFromRequest.equals("")) {
                    double meters = Double.parseDouble(metersFromRequest);
                    showConvertedValues(writer, meters, meters * 100, meters * 1000);
                } else if (!centimetersFromRequest.equals("")) {
                    double centimeters = Double.parseDouble(centimetersFromRequest);
                    showConvertedValues(writer, centimeters / 100, centimeters, centimeters * 10);
                } else {
                    double millimeters = Double.parseDouble(millimetersFromRequest);
                    showConvertedValues(writer, millimeters / 1000, millimeters / 10, millimeters);
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

    private void showConvertedValues(PrintWriter writer, double meters, double centimeters, double millimeters) {
        writer.println("<h2>Podana wartość w przeliczeniu na:</h2>");
        writer.println("<h3>metry: " + meters + "</h3>");
        writer.println("<h3>centrymentry: " + centimeters + "</h3>");
        writer.println("<h3>milimetry: " + millimeters + " </h3>");
    }

}