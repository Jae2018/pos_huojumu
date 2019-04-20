package com.huojumu.utils;

public class H5Order {

    public static String html = "<!DOCTYPE HTML>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "  <meta charset=UTF-8>\n" +
            "  <meta name=\"description\" content=\"\">\n" +
            "  <meta name=\"theme-color\" content=\"#fafafa\">\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "  <title></title>\n" +
            "  <script src=\"file://android_asset/jquery-3.3.1.min.js\"></script>\n" +
            "  <script src=\"file:///android_asset/html2canvas.js\"></script>\n" +
            "  <script type=\"text/javascript\">\n" +
            "    var per = {data};\n" +
            "    window.onload = function () {\n" +
            "      var tbody = document.getElementById('tbMain');\n" +
            "      for (var i = 0; i < per.length; i++) {\n" +
            "        var p = eval(per);\n" +
            "        var trow = getDataRow(p[i]);\n" +
            "        tbody.appendChild(trow);\n" +
            "      }\n" +
            "      getCapture();"+
            "    };\n" +
            "    function getCapture() {\n" +
            "      html2canvas(document.body).then(function(canvas) {\n" +
            "          var url = canvas.toDataURL();\n" +
            "          JSInterface.save(url);"+
            "      });\n" +
            "    }"+
            "    function getDataRow(h) {\n" +
            "      var div = document.createElement('div');\n" +
            "      var row = document.createElement('div');\n" +
            "      row.style.display = \"flex\";\n" +
            "      var idCell = document.createElement('label');\n" +
            "      idCell.innerHTML = h.proName;\n" +
            "      idCell.style.width = \"40%\";\n" +
            "      idCell.style.fontSize = \"24px\";\n"+
            "      row.appendChild(idCell);\n" +
            "      var nameCell = document.createElement('label');\n" +
            "      nameCell.innerHTML = h.number;\n" +
            "      nameCell.style.width = \"20%\";\n" +
            "      nameCell.style.textAlign = \"center\";\n" +
            "      nameCell.style.fontSize = \"24px\";\n"+
            "      row.appendChild(nameCell);\n" +
            "      var jobCell = document.createElement('label');\n" +
            "      jobCell.innerHTML = h.price;\n" +
            "      jobCell.style.width = \"20%\";\n" +
            "      jobCell.style.textAlign = \"center\";\n" +
            "      jobCell.style.fontSize = \"24px\";\n"+
            "      row.appendChild(jobCell);\n" +
            "      var delCell = document.createElement('label');\n" +
            "      delCell.innerHTML = h.number * h.price;\n" +
            "      delCell.style.width = \"20%\";\n" +
            "      delCell.style.textAlign = \"end\";\n" +
            "      delCell.style.fontSize = \"24px\";\n"+
            "      row.appendChild(delCell);\n" +
            "      div.appendChild(row);\n" +
            "      if (h.mats.length > 0) {\n" +
            "        console.log(JSON.stringify(h.mats));\n" +
            "        for (var j = 0; j < h.mats.length; j++) {\n" +
            "          var matCell = document.createElement('div');\n" +
            "          matCell.style.display = \"flex\";\n" +
            "          matCell.style.fontSize = 24;\n"+
            "          row.appendChild(matCell);\n" +
            "          var matNameCell = document.createElement('label');\n" +
            "          matNameCell.innerHTML = \"-\" + h.mats[j].matName;\n" +
            "          matNameCell.style.width = \"40%\";\n" +
            "          matNameCell.style.fontSize = \"24px\";\n"+
            "          matCell.appendChild(matNameCell);\n" +
            "          var matNumCell = document.createElement('label');\n" +
            "          matNumCell.innerHTML = h.number;\n" +
            "          matNumCell.style.width = \"20%\";\n" +
            "          matNumCell.style.textAlign = \"center\";\n" +
            "          matNumCell.style.fontSize = \"24px\";\n"+
            "          matCell.appendChild(matNumCell);\n" +
            "          var matPriceCell = document.createElement('label');\n" +
            "          matPriceCell.innerHTML = h.mats[j].ingredientPrice;\n" +
            "          matPriceCell.style.width = \"20%\";\n" +
            "          matPriceCell.style.textAlign = \"center\";\n" +
            "          matPriceCell.style.fontSize = \"24px\";\n"+
            "          matCell.appendChild(matPriceCell);\n" +
            "          var matCostCell = document.createElement('label');\n" +
            "          matCostCell.innerHTML = h.mats[j].ingredientPrice * h.number;\n" +
            "          matCostCell.style.width = \"20%\";\n" +
            "          matCostCell.style.textAlign = \"end\";\n" +
            "          matCostCell.style.fontSize = \"24px\";\n"+
            "          matCell.appendChild(matCostCell);\n" +
            "          div.appendChild(matCell);\n" +
            "        }\n" +
            "      }\n" +
            "      return div;\n" +
            "    }\n" +
            "  </script>\n" +
            "<body>\n" +
            "<div>\n" +
            "  <div style=\"width: 100%;height: 3.5rem;font-size: 40px;line-height: 3.5rem;padding-left: 10px;\">{1}</div>\n" +
            "  <div style=\"width: 100%;height: 5px;background-color: #222222;\"></div>\n" +
            "  <div style=\"padding-top: 5px;\">\n" +
            "    <label style=\"width: 100%;display: flex;font-size: 24px;\">收银员：{2}</label>\n" +
            "    <label style=\"width: 100%;display: flex;font-size: 24px;\">时间：{3}</label>\n" +
            "  </div>\n" +
            "  <div style=\"height: 1rem;width: 100%;\">\n" +
            "    <div style=\"margin-top: 5px;border: #222222 4px dashed;\"></div>\n" +
            "  </div>\n" +
            "  <div style=\"display: flex\">\n" +
            "    <label style=\"width: 40%;font-size: 24px;\">商品名称</label>\n" +
            "    <label style=\"width: 20%;text-align: center;font-size: 24px;\">数量</label>\n" +
            "    <label style=\"width: 20%;text-align: center;font-size: 24px;\">单价</label>\n" +
            "    <label style=\"width: 20%;text-align: end;font-size: 24px;\">金额</label>\n" +
            "  </div>\n" +
            "  <div id=\"tbMain\"></div>\n" +
            "  <div style=\"height: 1rem;width: 100%;\">\n" +
            "    <div style=\"margin-top: 5px;border: #222222 4px dashed;\"></div>\n" +
            "  </div>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>\n" +
            "\n";


}
