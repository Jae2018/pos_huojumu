package com.huojumu.utils;

public class H5Order {

    public static String html = "<!DOCTYPE HTML>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "  <meta charset=UTF-8>\n" +
            "  <meta name=\"description\" content=\"\">\n" +
            "  <meta name=\"theme-color\" content=\"#fafafa\">\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "  <title>table</title>\n" +
            "  <style>\n" +
            "    .order-no {\n" +
            "      width: 100%;\n" +
            "      height: 3.5rem;\n" +
            "      font-size: 40px;\n" +
            "      line-height: 3.5rem;\n" +
            "      padding-left: 10px;\n" +
            "    }\n" +
            "    .line1 {\n" +
            "      width: 100%;\n" +
            "      height: 5px;\n" +
            "      background-color: #222222;\n" +
            "    }\n" +
            "    .tap1 {\n" +
            "      padding-top: 5px;\n" +
            "    }\n" +
            "    .work-name {\n" +
            "      width: 100%;\n" +
            "      display: flex;\n" +
            "      font-size: 24px;\n" +
            "    }\n" +
            "    .line2-container {\n" +
            "      height: 1rem;\n" +
            "      width: 100%;\n" +
            "    }\n" +
            "    .line2 {\n" +
            "      margin-top: 5px;\n" +
            "      border: #222222 4px dashed;\n" +
            "    }\n" +
            "    .left-label {\n" +
            "      width: 40%;\n" +
            "      font-size: 24px;\n" +
            "    }\n" +
            "    .second-label {\n" +
            "      width: 20%;\n" +
            "      text-align: center;\n" +
            "      font-size: 24px;\n" +
            "    }\n" +
            "    .third-label {\n" +
            "      width: 20%;\n" +
            "      text-align: center;\n" +
            "      font-size: 24px;\n" +
            "    }\n" +
            "    .right-label {\n" +
            "      width: 20%;\n" +
            "      text-align: end;\n" +
            "      font-size: 24px;\n" +
            "    }\n" +
            "  </style>\n" +
            "  <script type=\"text/javascript\">\n" +
            "    var per = {data};\n" +
            "    window.onload = function () {\n" +
            "      var tbody = document.getElementById('tbMain');\n" +
            "      for (var i = 0; i < per.length; i++) {\n" +
            "        var p = eval(per);\n" +
            "        var trow = getDataRow(p[i]);\n" +
            "        tbody.appendChild(trow);\n" +
            "      }\n" +
            "    };\n" +
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
            "  <div class=\"order-no\">{1}</div>\n" +
            "  <div class=\"line1\"></div>\n" +
            "  <div class=\"tap1\">\n" +
            "    <label class=\"work-name\">收银员：{2}</label>\n" +
            "    <label class=\"work-name\">时间：{3}</label>\n" +
            "  </div>\n" +
            "  <div class=\"line2-container\">\n" +
            "    <div class=\"line2\"></div>\n" +
            "  </div>\n" +
            "  <div style=\"display: flex\">\n" +
            "    <label class=\"left-label\">商品名称</label>\n" +
            "    <label class=\"second-label\">数量</label>\n" +
            "    <label class=\"third-label\">单价</label>\n" +
            "    <label class=\"right-label\">金额</label>\n" +
            "  </div>\n" +
            "  <div id=\"tbMain\"></div>\n" +
            "  <div class=\"line2-container\">\n" +
            "    <div class=\"line2\"></div>\n" +
            "  </div>\n" +
            "  <div>\n" +
            "    <div>\n" +
            "      <label style=\"font-size:24px;\">消费金额：</label>\n" +
            "      <label style=\"float: right;font-size:24px;\">{4}</label>\n" +
            "    </div>\n" +
            "    <div>\n" +
            "      <label style=\"font-size:24px;\">实收金额：</label>\n" +
            "      <label style=\"float: right;font-size:24px;\">{5}</label>\n" +
            "    </div>\n" +
            "    <div>\n" +
            "      <label style=\"font-size:24px;\">优惠金额：</label>\n" +
            "      <label style=\"float: right;font-size:24px;\">{6}</label>\n" +
            "    </div>\n" +
            "    <div>\n" +
            "      <label style=\"font-size:24px;\">找 零：</label>\n" +
            "      <label style=\"float: right;font-size:24px;\">{7}</label>\n" +
            "    </div>\n" +
            "  </div>\n" +
            "  <div class=\"line2-container\">\n" +
            "    <div class=\"line2\"></div>\n" +
            "  </div>\n" +
            "  <div class=\"line1\"></div>\n" +
            "  <div style=\"width: 100%;margin-top: 5px;margin-bottom: 5px;\">\n" +
            "    <label style=\"position: absolute;font-size:24px;\">{8}</label>\n" +
            "    <img src=\"{9}\" alt=\"品牌logo\" style=\"width: 100%;\">\n" +
            "  </div>\n" +
            "  <div style=\"font-size:24px;\">7港9欢迎您的到来。我们再次从香港出发，希望搜集到各地的特色食品，港印全国。能7(去)香港(港)的(9)九龙喝一杯正宗的港饮是我们对每一位顾客的愿景。几百年来，香港作为东方接触世界的窗口，找寻并创造了一款款独具特色又流传世界的高品饮品。我们在全国超过十年的专业服务与坚持，与97回归共享繁华，秉承独到的调制方法，期许再一次与亲爱的你能擦出下一个十年火花。</div>\n" +
            "  <img src=\"{10}\" alt=\"公众号二维码\" style=\"width: 40%;margin-left: 30%;\">\n" +
            "  <div style=\"text-align: center;font-size:24px;\">投诉、加盟热线：010-62655878</div>\n" +
            "  <div style=\"margin-top: 12px;text-align: center;\">技术支持：火炬木科技</div>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>\n" +
            "\n";


}
