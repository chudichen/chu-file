package com.chudichen.chufile.model.dto;

import lombok.Data;

/**
 * 音频
 *
 * @author chudichen
 * @date 2021-01-21
 */
@Data
public class AudioInfoDTO {

    /** 名称 */
    private String title;
    /** 作者 */
    private String artist;
    /** 封面 */
    private String cover;
    /** 源头 */
    private String src;

    public static AudioInfoDTO buildDefaultAudioInfoDTO() {
        AudioInfoDTO audioInfoDTO = new AudioInfoDTO();
        audioInfoDTO.setTitle("未知歌曲");
        audioInfoDTO.setArtist("未知");
        audioInfoDTO.setCover("http://c.jun6.net/audio.png");
        return audioInfoDTO;
    }
}
