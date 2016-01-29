package com.duoec.docs.doclet.gitbook;

import com.duoec.docs.dto.BookSection;
import com.duoec.docs.helper.FileHelper;
import com.duoec.docs.logger.Logger;
import com.duoec.docs.doclet.IDoclet;
import com.duoec.docs.dto.ApiItem;
import com.duoec.docs.dto.Book;

import java.io.File;
import java.util.List;

/**
 * Created by ycoe on 16/1/27.
 */
public class GitBookDocletImpl implements IDoclet {
    private static final Logger logger = Logger.getInstance(GitBookDocletImpl.class);

    private static final boolean DEBUG = false;

    @Override
    public void createDocs(Book book) {
        String outputPath = book.getPath();
        File docDir = new File(outputPath);
        FileHelper.mkdir(docDir);
        logger.info("文档输出目录: {}", outputPath);

        String summary = BookMD.getSummaryString(book);
        if(!DEBUG){
            FileHelper.write(docDir, "SUMMARY.md", summary);
        }else{
            logger.info("\n" + summary); //测试输出
        }

        //Section的README.md
        List<BookSection> sections = book.getSections();
        if(sections != null){
            for (BookSection section : sections) {
                String sectionReadMe = BookSectionMD.getSectionSummaryString(section);
                File sectionDir = new File(section.getPath());
                if(!DEBUG){
                    FileHelper.write(sectionDir, "README.md", sectionReadMe);
                }else{
                    logger.info("\n" + sectionReadMe); //测试输出
                }

                //API md
                List<ApiItem> apis = section.getItems();
                if(apis != null){
                    for (ApiItem api : apis){
                        String apiMd = ApiMD.getApiString(api);
                        if(!DEBUG){
                            FileHelper.write(sectionDir, api.getFileName() + ".md", apiMd);
                        }else{
                            logger.info("\n" + apiMd); //测试输出
                        }
                    }
                }
            }
        }
    }
}
