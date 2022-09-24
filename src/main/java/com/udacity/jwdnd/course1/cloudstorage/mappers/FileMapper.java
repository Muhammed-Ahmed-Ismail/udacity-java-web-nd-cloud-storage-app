package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO files (filename,contenttype,filesize,userid,filedata) VALUES (#{fileName},#{contentType},#{fileSize},#{userId},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void storeFile(File file);

    @Select("SELECT filename from files WHERE userid=#{userId}")
    List<String> getStoredFileNames(int userId);

    @Select("SELECT fileid from files WHERE userid=#{userId}")
    List<Integer> getStoredFilesId(int userId);

    @Select("SELECT * FROM files WHERE fileid = #{fileId}")
    File getFileData(int fileId);

    @Delete("DELETE FROM files WHERE fileid = #{fileId} ")
    void deleteFile(int fileId);

    @Select("SELECT fileid FROM files WHERE filename=#{fileName}")
    Integer getFileId(String fileName);
}
