import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Book} from '../../shared/interfaces/book';
import {BookFile} from '../../shared/interfaces/book-file';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {BookComment} from '../../shared/interfaces/book-comment';
import {ApiService} from '../../_services/api.service';

@Component({
  selector: 'book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.scss']
})
export class BookDetailsComponent implements OnInit {
  book: Book | undefined;
  bookFiles: BookFile[] | undefined;
  comments: BookComment[] | undefined;
  form: any = {};

  constructor(private activatedRoute: ActivatedRoute,
              private sanitizer: DomSanitizer,
              private api: ApiService) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(d => {
      // tslint:disable-next-line:no-string-literal
      this.book = d['book'];
      // tslint:disable-next-line:no-string-literal
      this.bookFiles = d['bookFiles'];
      // tslint:disable-next-line:no-string-literal
      this.comments = d['comments'];
    });
  }

  previewUrl(bookFile: BookFile): SafeResourceUrl  {
    return this.sanitizer.bypassSecurityTrustResourceUrl('https://drive.google.com/file/d/' + bookFile.google_drive_id + '/preview');
  }

  downloadUrl(bookFile: BookFile): SafeResourceUrl  {
    return this.sanitizer.bypassSecurityTrustResourceUrl('https://drive.google.com/uc?id=' + bookFile.google_drive_id + '&export=download');
  }

  onClickSubmit(id: number): void {
    this.api.sendComment(id, this.form.text).subscribe(
      {
        next: value => {
          console.log(value);
          window.location.reload();
        },
        error: err => {
          console.log(err);
          window.location.reload();
        }
      }
    );
  }
}
