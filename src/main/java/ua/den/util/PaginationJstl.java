package ua.den.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationJstl extends TagSupport {
    private static final Logger LOG = LogManager.getLogger(PaginationJstl.class);

    private int pageNumber;
    private int currentPage;

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();

            out.write("<nav style='margin-top: 20px;' aria-label='Page navigation'>");
            out.write("<ul class='pagination justify-content-center'>");

            if (currentPage != 1) {
                out.write("<li class='page-item'>");
                out.write("<a class='page-link' href='/company/user/tickets/page/" + (currentPage - 1) + "' aria-label='Previous'>");
                out.write("<span aria-hidden='true'>&laquo;</span>");
                out.write("<span class='sr-only'>Previous</span>");
                out.write("</a>");
                out.write("</li>");
            }

            for (int i = 1; i <= pageNumber; i++) {
                if (i == currentPage) {
                    out.write("<li class='page-item active' aria-current='page'>");
                    out.write("<a class='page-link' href='#'>");
                    out.write(Integer.toString(i));
                    out.write("<span class='sr-only'>(current)</span>");
                    out.write("</a>");
                    out.write("</li>");

                    continue;
                }

                out.write("<li class='page-item'>");
                out.write("<a class='page-link' href='/company/user/tickets/page/" + i + "'>");
                out.write(Integer.toString(i));
                out.write("</a>");
                out.write("</li>");
            }

            if (currentPage != pageNumber) {
                out.write("<li class='page-item'>");
                out.write("<a class='page-link' href='/company/user/tickets/page/" + (currentPage + 1) + "' aria-label='Next'>");
                out.write("<span aria-hidden='true'>&raquo;</span>");
                out.write("<span class='sr-only'>Next</span>");
                out.write("</a>");
                out.write("</li>");
            }


            out.write("</ul></nav>");
        } catch (IOException ex) {
            LOG.error("Exception in JSTL {}", ex);
        }
        return SKIP_BODY;
    }
}
