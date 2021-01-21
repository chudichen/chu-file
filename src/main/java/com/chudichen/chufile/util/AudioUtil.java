package com.chudichen.chufile.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.URLUtil;
import com.chudichen.chufile.model.constant.ChuFileConstant;
import com.chudichen.chufile.model.dto.AudioInfoDTO;
import com.mpatric.mp3agic.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author chudichen
 * @date 2021-01-21
 */
@Slf4j
public class AudioUtil {

    public static AudioInfoDTO getAudioInfo(String url) throws Exception {
        String query = new URL(URLUtil.decode(url)).getQuery();

        if (query != null) {
            url = url.replace(query, URLUtil.encode(query));
        }

        // 如果音乐文件大小超出5M，则不解析此音乐
        long maxFileSize = 5 * 1024 * ChuFileConstant.AUDIO_MAX_FILE_SIZE_MB;
        if (com.chudichen.chufile.util.HttpUtil.getRemoteFileSize(url) > maxFileSize) {
            return AudioInfoDTO.buildDefaultAudioInfoDTO();
        }

        String fullFilePath = StringUtils.removeDuplicateSeparator(ChuFileConstant.TMP_FILE_PATH + ChuFileConstant.PATH_SEPARATOR + UUID.fastUUID());

        File file = new File(fullFilePath);
        FileUtil.mkParentDirs(file);
        HttpUtil.downloadFile(url, file);
        AudioInfoDTO audioInfoDTO = parseAudioInfo(file);
        audioInfoDTO.setSrc(url);
        file.deleteOnExit();
        return audioInfoDTO;
    }

    private static AudioInfoDTO parseAudioInfo(File file) throws IOException, UnsupportedTagException {
        AudioInfoDTO audioInfoDTO = AudioInfoDTO.buildDefaultAudioInfoDTO();

        Mp3File mp3File = null;
        try {
            mp3File = new Mp3File(file);
        } catch (InvalidDataException e) {
            if (log.isDebugEnabled()) {
                log.debug("无法解析的音频文件");
            }
        }

        if (mp3File == null) {
            return audioInfoDTO;
        }

        ID3v1 audioTag = null;
        if (mp3File.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            byte[] albumImage = id3v2Tag.getAlbumImage();
            if (albumImage != null) {
                audioInfoDTO.setCover("data:" + id3v2Tag.getAlbumImageMimeType() + ";base64," + Base64.encode(albumImage));
            }
            audioTag = id3v2Tag;
        }

        if (audioTag != null) {
            audioInfoDTO.setTitle(audioTag.getTitle());
            audioInfoDTO.setArtist(audioTag.getArtist());
        }
        return audioInfoDTO;
    }
}
