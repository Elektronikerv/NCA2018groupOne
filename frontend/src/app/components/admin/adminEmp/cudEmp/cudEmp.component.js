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
var CudEmpComponent = (function () {
    // toasterService: ToasterService
    function CudEmpComponent(router, formBuilder) {
        this.router = router;
        this.formBuilder = formBuilder;
    }
    CudEmpComponent.prototype.ngOnInit = function () {
        this.cudEmployeeForm = this.formBuilder.group({
            email: new forms_1.FormControl('', ng2_validation_1.CustomValidators.email),
            password: new forms_1.FormControl(ng2_validation_1.CustomValidators.required),
            firstName: new forms_1.FormControl(ng2_validation_1.CustomValidators.required),
            lastName: new forms_1.FormControl(ng2_validation_1.CustomValidators.required),
            manager: new forms_1.FormControl(ng2_validation_1.CustomValidators.required),
            position: new forms_1.FormControl(ng2_validation_1.CustomValidators.required),
            name: new forms_1.FormControl(ng2_validation_1.CustomValidators.required),
            phoneNumber: new forms_1.FormControl(ng2_validation_1.CustomValidators.required),
            registrationDate: new forms_1.FormControl(ng2_validation_1.CustomValidators.required),
            street: new forms_1.FormControl(ng2_validation_1.CustomValidators.required, forms_1.Validators.maxLength(256)),
            house: ['', [forms_1.Validators.required]],
            floor: new forms_1.FormControl('', ng2_validation_1.CustomValidators.digits),
            flat: new forms_1.FormControl('', ng2_validation_1.CustomValidators.digits)
        });
    };
    CudEmpComponent.prototype.validateField = function (field) {
        console.log(this.cudEmployeeForm.get(field));
        return this.cudEmployeeForm.get(field).valid || !this.cudEmployeeForm.get(field).dirty;
    };
    // public config1 : ToasterConfig = new ToasterConfig({
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
    CudEmpComponent.prototype.submitForm = function () {
    };
    ;
    CudEmpComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'cudEmp',
            templateUrl: 'cudEmp.component.html',
            styleUrls: ['cudEmp.component.css']
        }), 
        __metadata('design:paramtypes', [router_1.Router, forms_1.FormBuilder])
    ], CudEmpComponent);
    return CudEmpComponent;
}());
exports.CudEmpComponent = CudEmpComponent;
//# sourceMappingURL=cudEmp.component.js.map