package dice_chess.TestSimulations;
// Java program to write data in excel sheet using java code

import java.io.File;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import static dice_chess.Constant.Constant.*;

public class ExcelWriter {


    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public void writeExcel() throws Exception
    {

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Game sheet3");

        // This data needs to be written (Object[])
        Map<String, Object[]> studentData
                = new TreeMap<String, Object[]>();

        studentData.put(
                "1",
                new Object[] { "GAME SETTINGS" });

        studentData.put("2", new Object[] { "AI_White = " + AI_WHITE, "DEPTH_WHITE " + DEPTH_WHITE,"AI Black = " + AI_BLACK , "DEPTH_BLACK" +
                DEPTH_BLACK, "EVALUATION EXPECTIMAX " + EVALUATION_FUNCTION_EXPECTI_MAX,
                "EVALUATION EXPECIMINIMAX " + EVALUATION_FUNCTION_EXPECTI_MINIMAX,
                "EVALUATION MINIMAX " + EVALUATION_FUNCTION_MINIMAX  });

        studentData.put(
                "3",
                new Object[] { "" });

        studentData.put("4", new Object[] {"GAME#", "stepsW", "stepsB", "BlackWin", "WhiteWin" });

        int point = 4;

        HSSFCellStyle style = createStyleForTitle(workbook);
        Row row;

        row = sheet.createRow(0);
        row.createCell(0).setCellValue("GAME SETTINGS");
        row = sheet.createRow(1);
        row.createCell(0).setCellValue("AI_White = " + AI_WHITE);
        row.createCell(1).setCellValue("DEPTH_WHITE " + DEPTH_WHITE);
        row.createCell(2).setCellValue("AI Black = " + AI_BLACK);
        row.createCell(3).setCellValue("DEPTH_BLACK" + DEPTH_BLACK);
        row.createCell(4).setCellValue("EVALUATION EXPECTIMAX " + EVALUATION_FUNCTION_EXPECTI_MAX);
        row.createCell(5).setCellValue("EVALUATION EXPECIMINIMAX " + EVALUATION_FUNCTION_EXPECTI_MINIMAX);
        row.createCell(6).setCellValue("EVALUATION MINIMAX " + EVALUATION_FUNCTION_MINIMAX);

        row = sheet.createRow(2);
        row.createCell(0).setCellValue("Total White win = " + TOTAL_WIN_WHITE);
        row.createCell(1).setCellValue("Total White win = " + TOTAL_WIN_BLACK);
        row = sheet.createRow(3);
        row.createCell(0).setCellValue("GAME#");
        row.createCell(1).setCellValue("stepsW");
        row.createCell(2).setCellValue("stepsB");
        row.createCell(3).setCellValue("BlackWin");
        row.createCell(4).setCellValue("WhiteWin");


        System.out.println(LIST_GAMES.size());
        for (int i = 0; i < LIST_GAMES.size(); i++) {
            GameInfo gameInfo = LIST_GAMES.get(i);
            row = sheet.createRow(point++);
            Cell cell = row.createCell(0);
            cell.setCellValue(gameInfo.gameCounter);
            cell = row.createCell(1);
            cell.setCellValue(gameInfo.total_white_step);
            cell = row.createCell(2);
            cell.setCellValue(gameInfo.total_black_step);
            cell = row.createCell(3);
            cell.setCellValue(gameInfo.blackWins);
            cell = row.createCell(4);
            cell.setCellValue(!gameInfo.blackWins);

        }




        // .xlsx is the format for Excel Sheets...
        // writing the workbook into the file...
        File file = new File("C:/demo/game_data.xls");
        file.getParentFile().mkdir();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());
    }
}

