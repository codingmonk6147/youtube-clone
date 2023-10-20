import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../video.service';

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrls: ['./video-detail.component.css']
})
export class VideoDetailComponent implements OnInit {

  videoUrl!: string;
  thumbnailUrl!: string;
  videoId!: string;
  videoAvailable: boolean =false;
  videoTitle!: string;
  tags: Array<string>=[];
  description!: string;

  date : any = new Date();
  
  constructor(private videoService: VideoService,
    private route: ActivatedRoute){
      this.videoId = this.route.snapshot.params['videoId'];


      this.videoService.getVideo(this.videoId).subscribe( data => {
        this.videoUrl = data.videoUrl;
       this.videoAvailable = true;

       this.videoTitle = data.title;
       this.tags = data.tags;
       this.description= data.description;
      })

    }

    ngOnInit(): void {
      
    }


}
