import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { NgxFileDropEntry, FileSystemFileEntry, FileSystemDirectoryEntry } from 'ngx-file-drop';
import {Observable} from "rxjs";

import {UploadVideoResponse} from "./upload-video/UploadVideoResponse";
import { VideoDto } from './video-dto';


@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

    public uploadVideo(fileEntry: File): Observable<UploadVideoResponse> {


      const formData  = new FormData();
      formData.append('file',fileEntry, fileEntry.name);


      return this.httpClient.post<UploadVideoResponse>('http://localhost:8080/api/videos',formData);


    }

    // public getVideo(videoId: string): Observable<VideoDto> {
    //   return this.httpClient.get<VideoDto>("http://localhost:8080/api/videos/" + videoId);
    // }


    public uploadThumbnail(selectedFile: File, videoId: string): Observable<string> {
      const fd = new FormData();
      fd.append('file', selectedFile, selectedFile.name);
      fd.append('videoId', videoId);
      return this.httpClient.post('http://localhost:8080/api/videos/thumbnail', fd,
        {
         
          responseType: 'text'
        });
    }

    getVideo(videoId:String){
      return this.httpClient.get<VideoDto>("http://localhost:8080/api/videos/"+videoId);
    }

    saveVideo(videoMetaData : VideoDto):Observable<VideoDto>{
      return this.httpClient.put<VideoDto>("http://localhost:8080/api/videos/",videoMetaData)
    }
}
