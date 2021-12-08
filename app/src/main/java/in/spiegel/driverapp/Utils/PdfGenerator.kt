package `in`.spiegel.driverapp.Utils

import `in`.spiegel.driverapp.Model.HistoryModel
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import android.widget.Toast

import android.content.ActivityNotFoundException
import android.content.Context

import com.itextpdf.text.pdf.ColumnText

import com.itextpdf.text.pdf.PdfContentByte

import com.itextpdf.text.pdf.PdfPageEventHelper

import com.itextpdf.text.pdf.PdfPCell

import com.itextpdf.text.pdf.GrayColor

import com.itextpdf.text.pdf.PdfPTable

import android.os.Environment
import com.itextpdf.text.*
import java.lang.Exception


/**
 * Created by Vijay on 12/8/2021.
 */
class PdfGenerator() {
    private var pdfWriter: PdfWriter? = null

    //we will add some products to arrayListRProductModel to show in the PDF document

    fun createPDF(context: Context?, reportName: String,date:String,historyModel: HistoryModel): Boolean {
        try {
            //creating a directory in SD card
            val mydir: File = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/VIJ/REPORT_PRODUCT/"
            ) //PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
            if (!mydir.exists()) {
                mydir.mkdirs()
            }

            //getting the full path of the PDF report name
            val mPath = ((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath
                    + "/VIJ/REPORT_PRODUCT/" //PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
                    ) + reportName + ".pdf") //reportName could be any name

            //constructing the PDF file
            val pdfFile = File(mPath)

            //Creating a Document with size A4. Document class is available at  com.itextpdf.text.Document
            val document = Document(PageSize.A4)

            //assigning a PdfWriter instance to pdfWriter
            pdfWriter = PdfWriter.getInstance(document, FileOutputStream(pdfFile))

            //PageFooter is an inner class of this class which is responsible to create Header and Footer
            val event = PageHeaderFooter()
            pdfWriter!!.pageEvent = event

            //Before writing anything to a document it should be opened first
            document.open()

            //Adding meta-data to the document
            addMetaData(document)
            //Adding Title(s) of the document
            addTitlePage(document,date)
            //Adding main contents of the document
            addContent(document,historyModel)
            //Closing the document
            document.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    /**
     * iText allows to add metadata to the PDF which can be viewed in your Adobe Reader. If you right click
     * on the file and to to properties then you can see all these information.
     * @param document
     */
    private fun addMetaData(document: Document) {
        document.addTitle("All Product Names")
        document.addSubject("none")
        document.addKeywords("Java, PDF, iText")
        document.addAuthor("Vijay")
        document.addCreator("StaticValue.USER_MODEL.getName()")
    }

    /**
     * In this method title(s) of the document is added.
     * @param document
     * @throws DocumentException
     */
    @Throws(DocumentException::class)
    private fun addTitlePage(document: Document,date: String) {
        val paragraph = Paragraph()

        // Adding several title of the document. Paragraph class is available in  com.itextpdf.text.Paragraph
        var childParagraph = Paragraph(
            "Driver App",
            Font(Font.FontFamily.TIMES_ROMAN, 22f, Font.BOLD)
        ) //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.alignment = Element.ALIGN_CENTER
        paragraph.add(childParagraph)
        childParagraph = Paragraph(
            "Invoice",
            Font(Font.FontFamily.TIMES_ROMAN, 18f,Font.BOLD)
        ) //public static Font FONT_SUBTITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
        childParagraph.alignment = Element.ALIGN_CENTER
        paragraph.add(childParagraph)
        childParagraph = Paragraph("Report generated on: $date", Font(Font.FontFamily.TIMES_ROMAN, 18f,Font.BOLD))
        childParagraph.alignment = Element.ALIGN_CENTER
        paragraph.add(childParagraph)
        addEmptyLine(paragraph, 2)
        paragraph.alignment = Element.ALIGN_CENTER
        document.add(paragraph)
        //End of adding several titles
    }

    /**
     * In this method the main contents of the documents are added
     * @param document
     * @throws DocumentException
     */
    @Throws(DocumentException::class)
    private fun addContent(document: Document,historyModel: HistoryModel) {
        val reportBody = Paragraph()
        reportBody.font =
            Font(Font.FontFamily.TIMES_ROMAN, 12f,Font.NORMAL) //public static Font FONT_BODY = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL);

        // Creating a table
        createTable(reportBody, historyModel)

        // now add all this to the document
        document.add(reportBody)
    }

    /**
     * This method is responsible to add table using iText
     * @param reportBody
     * @throws BadElementException
     */
    @Throws(BadElementException::class)
    private fun createTable(reportBody: Paragraph,historyModel: HistoryModel) {
        val columnWidths = floatArrayOf(
            5f,
            5f
        ) //total 4 columns and their width. The first three columns will take the same width and the fourth one will be 5/2.
        val table = PdfPTable(columnWidths)
        table.widthPercentage = 100f //set table with 100% (full page)
        table.defaultCell.isUseAscender = true


        //Adding table headers
        var cell = PdfPCell(
            Phrase(
                "Tittle",  //Table Header
                Font(Font.FontFamily.TIMES_ROMAN, 12f,Font.BOLD)
            )
        ) //Public static Font FONT_TABLE_HEADER = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
        cell.horizontalAlignment = Element.ALIGN_CENTER //alignment
        cell.backgroundColor = GrayColor(0.75f) //cell background color
        cell.fixedHeight = 30f //cell height
        table.addCell(cell)
        cell = PdfPCell(
            Phrase(
                "Details",
                Font(Font.FontFamily.TIMES_ROMAN, 12f,Font.BOLD)
            )
        )
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.backgroundColor = GrayColor(0.75f)
        cell.fixedHeight = 30f
        table.addCell(cell)


        //End of adding table headers

        //This method will generate some static data for the table
       // generateTableData()

        //Adding data into table
       //
         cell = PdfPCell(Phrase("Pickup"))
         cell.fixedHeight = 28f
         table.addCell(cell)

        cell = PdfPCell(Phrase(historyModel.pickup))
        cell.fixedHeight = 28f
        table.addCell(cell)

        cell = PdfPCell(Phrase("Drop"))
        cell.fixedHeight = 28f
        table.addCell(cell)

        cell = PdfPCell(Phrase(historyModel.drop))
        cell.fixedHeight = 28f
        table.addCell(cell)

        cell = PdfPCell(Phrase("Distance"))
        cell.fixedHeight = 28f
        table.addCell(cell)

        cell = PdfPCell(Phrase(historyModel.distance))
        cell.fixedHeight = 28f
        table.addCell(cell)

        cell = PdfPCell(Phrase("Fare"))
        cell.fixedHeight = 28f
        table.addCell(cell)

        cell = PdfPCell(Phrase(historyModel.fare))
        cell.fixedHeight = 28f
        table.addCell(cell)
        reportBody.add(table)
    }

    /**
     * This method is used to add empty lines in the document
     * @param paragraph
     * @param number
     */

    private fun addEmptyLine(paragraph: Paragraph, number: Int) {
        for (i in 0 until number) {
            paragraph.add(Paragraph(" "))
        }
    }

    /**
     * This is an inner class which is used to create header and footer
     * @author XYZ
     */
    internal class PageHeaderFooter() : PdfPageEventHelper() {
        var ffont: Font = Font(Font.FontFamily.UNDEFINED, 5f, Font.ITALIC)
        override fun onEndPage(writer: PdfWriter, document: Document) {
            /**
             * PdfContentByte is an object containing the user positioned text and graphic contents
             * of a page. It knows how to apply the proper font encoding.
             */
            val cb = writer.directContent

            /**
             * In iText a Phrase is a series of Chunks.
             * A chunk is the smallest significant part of text that can be added to a document.
             * Most elements can be divided in one or more Chunks. A chunk is a String with a certain Font
             */
            val footer_poweredBy = Phrase(
                "Powered By Vijay",
                Font(Font.FontFamily.UNDEFINED, 7f, Font.ITALIC)
            ) //public static Font FONT_HEADER_FOOTER = new Font(Font.FontFamily.UNDEFINED, 7, Font.ITALIC);
            val footer_pageNumber =
                Phrase("Page " + document.pageNumber,  Font(Font.FontFamily.UNDEFINED, 7f, Font.ITALIC))

            // Header
            // ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, header,
            // (document.getPageSize().getWidth()-10),
            // document.top() + 10, 0);

            // footer: show page number in the bottom right corner of each age
            ColumnText.showTextAligned(
                cb, Element.ALIGN_RIGHT,
                footer_pageNumber,
                (document.pageSize.width - 10),
                document.bottom() - 10, 0f
            )
            //			// footer: show page number in the bottom right corner of each age
            ColumnText.showTextAligned(
                cb, Element.ALIGN_CENTER,
                footer_poweredBy, ((document.right() - document.left()) / 2
                        + document.leftMargin()), document.bottom() - 10, 0f
            )
        }
    }

    /**
     * Generate static data for table
     */


   data class ProductModel(var s: String,var s1: String,var s2: String,var s3: String)
}

