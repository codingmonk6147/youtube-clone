export interface VideoDto {
  id: string;
  userId: string;
  title: string;
  description: string;
  tags: Array<string>;
  videoStatus: string;
  url: string;
  thumbnailUrl: string;
  likeCount: number;
  dislikeCount: number;
  }