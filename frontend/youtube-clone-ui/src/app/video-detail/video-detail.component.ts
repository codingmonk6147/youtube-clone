import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../video.service';
import { Subscription } from 'rxjs';
import { UserService } from '../user.service';

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrls: ['./video-detail.component.css']
})
export class VideoDetailComponent implements OnInit {
  subscribeToUserObservable: Subscription = new Subscription;
  videoUrl!: string;
  thumbnailUrl!: string;
  videoId!: string | '';
  videoAvailable: boolean =false;
  title!: string;
  tags: Array<string>=[];
  description!: string;
  likeCount: number = 0;
  dislikeCount: number = 0;
  showSubscribeButton: boolean = true;
  showUnSubscribeButton: boolean = false;

  date : any = new Date();
  
  constructor(private userService: UserService,private videoService: VideoService,
    private route: ActivatedRoute){
      this.videoId = this.route.snapshot.params["videoId"];
      console.log("this is videoId", this.videoId);

      this.videoService.getVideo(this.videoId).subscribe( data => {
        this.videoUrl = data.url;
       this.videoAvailable = true;

       this.title = data.title;
       this.tags = data.tags;
       this.description= data.description;
      })

    }

    ngOnInit(): void {
      
    }

    subscribeToUser() {
      this.subscribeToUserObservable = this.userService.subscribeToUser( )
        .subscribe(() => {
          this.showSubscribeButton = false;
          this.showUnSubscribeButton = true;
        })
    }
    ngOnDestroy(): void {
      this.subscribeToUserObservable.unsubscribe();
    }
  
    likeVideo() {
      this.videoService.likeVideo(this.videoId).subscribe(data => {
        this.likeCount = data.likeCount;
        this.dislikeCount = data.dislikeCount;
      })
    }
  
    dislikeVideo() {
      this.videoService.dislikeVideo(this.videoId).subscribe(data => {
        this.likeCount = data.likeCount;
        this.dislikeCount = data.dislikeCount;
      })
    }


}
