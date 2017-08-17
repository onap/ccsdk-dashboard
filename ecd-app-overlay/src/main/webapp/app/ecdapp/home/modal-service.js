/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2017 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ============LICENSE_END=========================================================
 *
 *  ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/
angular.module("modalServices",[]).service('modalService', ['$modal', function ($modal) {

	/* 
	 * Defines the same functions available in DS1 using DS2 icons etc.
	 * Relies on templates defined in ecd_popup_templates.html
	 */
	
	var ModalInstanceCtrl = function ($scope, $modalInstance, $rootScope, items) {
		// Pass thru to template as object "msg"
		$scope.msg = items;
		
	    $scope.cancel = function () {
	        $modalInstance.dismiss('cancel');
	    };
	};
	
	this.showIconTitleTextOkModal = function (icon, title, text) {
    	var modalInstance = $modal.open({
			templateUrl: 'ecd_title_text_ok_modal.html',
			controller: ModalInstanceCtrl,
			sizeClass: 'modal-small',
			resolve: {
                items: function () {
					return {
						icon: icon,
						title: title,
	                   	text:  text
	                };
                }
	        }
		});
    };

	this.showFailure = function (title, text) {
		this.showIconTitleTextOkModal('icon-primary-alert', title, text);
	};

	this.showSuccess = function (title, text) {
		this.showIconTitleTextOkModal('icon-primary-approval', title, text);
	};
    
    /* Replicate modals defined by ds2-modal/modalService.js */
	errorPopUp = function(text) {
		this.showFailure('Error', text);
	};	
	successPopUp = function(text) {
		this.showSuccess('Success', text);
	};
		
	this.popupConfirmWin = function(title, text, callback) {
    	var modalInstance = $modal.open({
			templateUrl: 'ecd_title_text_ok_cancel_modal.html',
			controller: ModalInstanceCtrl,
			sizeClass: 'modal-small',
			resolve: {
                items: function () {
					return {
						icon: 'icon-primary-questionmark',
						title: title,
	                   	text:  text
	                };
                }
	        }
		});
    	var args = Array.prototype.slice.call(arguments, 0);
   		args.splice(0, 3); 
   		modalInstance.result.then(function(){
   			callback.apply(null, args);
   		}, function() {
   			//
   		})['finally'](function(){
   			modalInstance = undefined;
   		});
   	};
   		
 }]);
