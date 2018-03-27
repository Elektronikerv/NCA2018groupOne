import { Component, OnInit } from '@angular/core';
import { VerificationService } from "../../../service/verification.service"; 
import { CustomToastService } from "../../../service/customToast.service";
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-verify-email',
  templateUrl: './verify-email.component.html',
  styleUrls: ['./verify-email.component.css']
})
export class VerifyEmailComponent implements OnInit {

  email: string;
  hash: string;

  constructor(private customToastService: CustomToastService,
              private activatedRouter: ActivatedRoute,
              private router: Router,
              private verificationService : VerificationService) { }

  ngOnInit() {
    this.getParams();
    this.verificationService.verifyEmail(this.email, this.hash).subscribe(_ => {
      this.customToastService.setMessage('Your email is verified!');
      this.router.navigate(['/landing']);
    })
  }

  getParams() {
    this.activatedRouter.queryParams.subscribe(params => {
      this.email = params['email'];
      this.hash = params['hash'];
    });
  }
}
