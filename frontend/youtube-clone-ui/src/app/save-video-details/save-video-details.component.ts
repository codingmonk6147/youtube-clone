import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Subscription, BehaviorSubject } from 'rxjs';
import { COMMA, ENTER } from "@angular/cdk/keycodes";
import { VideoService } from '../video.service';
import { ActivatedRoute } from '@angular/router';
import { MatChipInputEvent } from '@angular/material/chips';

import {MatChipEditedEvent, MatChipsModule} from '@angular/material/chips';
import { VideoDto } from '../video-dto';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-save-video-details',
  templateUrl: './save-video-details.component.html',
  styleUrls: ['./save-video-details.component.css']
})
export class SaveVideoDetailsComponent {

  saveVideoForm!: FormGroup;
  title: FormControl = new FormControl('');
  description: FormControl = new FormControl('');
  videoStatus: FormControl = new FormControl('');
  
  
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  tags: string[] = [];


  selectedFile!: File;
  selectedFileName = '';

  uploadThumbnailSubscription!: Subscription;

  videoUrl!: string;
  thumbnailUrl!: string;
  videoId!: string;
  videoUrlAvailable!: boolean;
  fileUploaded!: boolean;

  showVideoUrl = false;


  constructor(private videoService: VideoService,
    private route: ActivatedRoute,private matSnackBar: MatSnackBar,) {
      this.videoId = this.route.snapshot.params['videoId'];


      this.videoService.getVideo(this.videoId).subscribe( data => {
        this.videoUrl = data.videoUrl;
        this.thumbnailUrl = data.thumbnailUrl;
        this.videoUrlAvailable = true;
      })

      
    this.saveVideoForm = new FormGroup({
      title: this.title,
      description: this.description,
      videoStatus: this.videoStatus,
    })
  }


  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) {
      this.tags.push(value.trim());
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(fruit: string): void {
    const index = this.tags.indexOf(fruit);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.selectedFileName = this.selectedFile.name;
  }

  onUpload() {
    this.uploadThumbnailSubscription = this.videoService.uploadThumbnail(this.selectedFile, this.videoId)
      .subscribe(data => {
        console.log(data);
        });
      };

      saveVideo() {
        
        const videoMetaData: VideoDto = {
          "id": this.videoId,
          "title": this.saveVideoForm.get('title')?.value,
          "description": this.saveVideoForm.get('description')?.value,
          "tags": this.tags,
          "videoStatus": this.saveVideoForm.get('videoStatus')?.value,
          "videoUrl": this.videoUrl,
          "thumbnailUrl": this.thumbnailUrl,
         
        }
        this.videoService.saveVideo(videoMetaData).subscribe(data => {
          this.showVideoUrl = true;
          this.matSnackBar.open("Video Metadata Updated Successfully", "OK");
        });
      }
  

}
