import { Injectable } from '@angular/core';
import {
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class MapPointService {
  url = environment.host + '/api/mappoints';

  constructor(private router: Router, private http: HttpClient) {}

  public getMapPoints(): any {
    const promise = new Promise<any>((resolve, reject) => {
      // FIXME: Move this to other ENDPOINT
      const filter = {
        visible: [true],
      };
      this.http
        .get(
          `${this.url}/getfiltered?filter=${encodeURI(JSON.stringify(filter))}`
        )
        .toPromise()
        .then(
          (res) => {
            resolve(res);
          },
          (err) => {
            reject(err);
          }
        );
    });

    return promise;
  }
}
