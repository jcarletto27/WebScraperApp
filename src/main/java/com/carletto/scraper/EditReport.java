package com.carletto.scraper;

import com.carletto.scraper.utils.DateList;
import com.carletto.scraper.utils.HostName;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcarlett on 3/3/2017.
 */
public class EditReport {

    public HtmlPage page;
    public List<HtmlSelect> selectList = new ArrayList<>();
    public List<String> elementList = new ArrayList<>();

    //SubmitButton Javascript
    public String javascriptSubmit = "Clicking.Key(event,'MasterPageReports_m_mFr_btnSave',true)";
    public DomElement submitInput;

    public void editSettings(String url) {
        String startDay = "MasterPageReports_m_mFr_reportParameters1_startDay";
        String endDay = "MasterPageReports_m_mFr_reportParameters1_endDay";
        String startMonth = "MasterPageReports_m_mFr_reportParameters1_startMonth";
        String endMonth = "MasterPageReports_m_mFr_reportParameters1_endMonth";
        String startYear = "MasterPageReports_m_mFr_reportParameters1_startYear";
        String endYear = "MasterPageReports_m_mFr_reportParameters1_endYear";
        String submit = "MasterPageReports_m_mFr_btnSave";


        elementList.add(startDay);
        elementList.add(endDay);
        elementList.add(startMonth);
        elementList.add(endMonth);
        elementList.add(startYear);
        elementList.add(endYear);

        try {


            //Machine name
            String hostname = HostName.getHostName();


            //Webclient and options
            WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.waitForBackgroundJavaScriptStartingBefore(2000);
            webClient.getOptions().setRedirectEnabled(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);

            webClient.setAjaxController(new NicelyResynchronizingAjaxController());


            //Cookies
            CookieManager manager = webClient.getCookieManager();
            manager.setCookiesEnabled(true);


            //Credentials
            DefaultCredentialsProvider provider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
            provider.addNTLMCredentials("copyrm", "cr5220", null, -1, hostname, "corp");
            webClient.setCredentialsProvider(provider);


            //Get the page
            page = webClient.getPage(url);


            //System.out.println(page.getWebResponse().getContentAsString());


            //Gather your elements, and move what we need to new list
            DomNodeList<DomElement> list = page.getElementsByTagName("select");
            DomNodeList<DomElement> submitlist = page.getElementsByTagName("table");

            for (DomElement node : submitlist) {
                if (node.getId().equals(submit)) {
                    //System.out.println(node.getId());
                    submitInput = node;
                }
            }

            for (DomElement anchor : list) {
                for (String element : elementList) {

                    if (element.equals(anchor.getId())) {
                        //casting item to HtmlSelect, to give us options.
                        selectList.add((HtmlSelect) anchor);
                    }
                }
            }


            //gather options for each select Item
            for (HtmlSelect select : selectList) {
                //System.out.println(select.getId() + " " + select.getDefaultValue());

                DateList.addCurrentDatesPair(select.getId().replace(" ", ""), select.getDefaultValue().replace(" ", ""));
            }


            //Page endpage = executeJavaScript(page, javascriptSubmit);


        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public List<HtmlSelect> getSelectList() {
        return selectList;
    }

    public void setField(HtmlSelect select, String value) {
        HtmlOption option = select.getOptionByValue(value);
        select.setSelectedAttribute(option, true);
    }

    public List<String> getElementList() {
        return elementList;
    }

    public HtmlPage submit() {

        try {
//            page.executeJavaScript("Clicking.Key(event, 'MasterPageReports_m_mFr_btnSave',true);");
            // System.out.println("submitting!");
            return submitInput.click();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
