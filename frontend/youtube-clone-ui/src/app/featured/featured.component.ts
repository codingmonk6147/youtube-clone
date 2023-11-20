import { Component, OnDestroy } from '@angular/core';
import { VideoDto } from '../upload-video/VideoDto';
import { Subscription } from 'rxjs';
import { VideoService } from '../video.service';

@Component({
  selector: 'app-featured',
  templateUrl: './featured.component.html',
  styleUrls: ['./featured.component.css']
})
export class FeaturedComponent implements OnDestroy {

  videos: Array<VideoDto> = [];
  getAllVideosSubscription: Subscription;

  constructor(private videoService: VideoService) {
    this.getAllVideosSubscription = videoService.getAllVideos().subscribe(data => {
      this.videos = data;
    });
  }

  ngOnDestroy(): void {
    this.getAllVideosSubscription.unsubscribe();
  }
}