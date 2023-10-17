import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { NgxFileDropEntry, FileSystemFileEntry, FileSystemDirectoryEntry } from 'ngx-file-drop';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

    public uploadVideo(fileEntry: File): Observable<any> {


      const formData  = new FormData();
      formData.append('file',fileEntry, fileEntry.name);


      return this.httpClient.post('http://localhost:8080/api/videos',formData);


    }
}
