"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var forms_1 = require("@angular/forms");
var router_1 = require("@angular/router");
var ng2_validation_1 = require("ng2-validation");
var PublishSiteInfoComponent = (function () {
    function PublishSiteInfoComponent(router, formBuilder) {
        this.router = router;
        this.formBuilder = formBuilder;
    }
    PublishSiteInfoComponent.prototype.ngOnInit = function () {
        this.siteInfoPublishForm = this.formBuilder.group({
            header: new forms_1.FormControl(ng2_validation_1.CustomValidators.required),
            text: new forms_1.FormControl(ng2_validation_1.CustomValidators.required),
            type: new forms_1.FormControl(ng2_validation_1.CustomValidators.required)
        });
    };
    //   public config1 : ToasterConfig = new ToasterConfig({
    //     positionClass: 'toast-top-center'
    //   });
    //   popToast() {
    //     var toast: Toast = {
    //       type: 'info',
    //       title: 'Hello from Toast Title',
    //       body: 'Hello from Toast Body'
    //     };
    //     this.toasterService.pop(toast);
    //   }
    PublishSiteInfoComponent.prototype.submitForm = function () {
    };
    ;
    PublishSiteInfoComponent.prototype.validateField = function (field) {
        console.log(this.siteInfoPublishForm.get(field));
        return this.siteInfoPublishForm.get(field).valid || !this.siteInfoPublishForm.get(field).dirty;
    };
    PublishSiteInfoComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'publishSiteInfo',
            templateUrl: 'publishSiteInfo.component.html',
            styleUrls: ['publishSiteInfo.component.css']
        }), 
        __metadata('design:paramtypes', [router_1.Router, forms_1.FormBuilder])
    ], PublishSiteInfoComponent);
    return PublishSiteInfoComponent;
}());
exports.PublishSiteInfoComponent = PublishSiteInfoComponent;
//# sourceMappingURL=publishSiteInfo.component.js.map