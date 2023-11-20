import { Component, Input } from '@angular/core';
import { VideoDto } from '../upload-video/VideoDto';


@Component({
  selector: 'app-video-card',
  templateUrl: './video-card.component.html',
  styleUrls: ['./video-card.component.css']
})
export class VideoCardComponent {
  @Input()
  video!: VideoDto;

    constructor(){
      console.log("this is video in video card", this.video);
    }
}
