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
// import {User} from "../../model/user.model";
// import {UserService} from "../../service/user.service";
var CudOfficeComponent = (function () {
    //     // formBuilder: FormBuilder;
    function CudOfficeComponent(router, formBuilder) {
        this.router = router;
        this.formBuilder = formBuilder;
    }
    CudOfficeComponent.prototype.ngOnInit = function () {
        this.cudOfficeForm = this.formBuilder.group({
            name: new forms_1.FormControl(ng2_validation_1.CustomValidators.required),
            street: new forms_1.FormControl(ng2_validation_1.CustomValidators.required, forms_1.Validators.maxLength(256)),
            house: ['', [forms_1.Validators.required]],
            floor: ['', ng2_validation_1.CustomValidators.number],
            flat: new forms_1.FormControl(ng2_validation_1.CustomValidators.number)
        });
    };
    CudOfficeComponent.prototype.submitForm = function () {
    };
    ;
    CudOfficeComponent.prototype.validateField = function (field) {
        console.log(this.cudOfficeForm.get(field));
        return this.cudOfficeForm.get(field).valid || !this.cudOfficeForm.get(field).dirty;
    };
    CudOfficeComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'cudOffice',
            templateUrl: 'cudOffice.component.html',
            styleUrls: ['cudOffice.component.css']
        }), 
        __metadata('design:paramtypes', [router_1.Router, forms_1.FormBuilder])
    ], CudOfficeComponent);
    return CudOfficeComponent;
}());
exports.CudOfficeComponent = CudOfficeComponent;
//# sourceMappingURL=cudOffice.component.js.map