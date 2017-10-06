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
(function() {
    'use strict';
    
   appDS2.directive("orgChart", supervisorOrgChart);
    supervisorOrgChart.$inject = ['$http', '$templateCache', 'ExecutionService'];

    function supervisorOrgChart($http, $templateCache, ExecutionService) {
        return {
            restrict: 'E',
            scope: {
                data: '=',
                getCall: '&',
                modalCall: '&',
                status: '='
            },

            link: function(scope, elem, attrs) {
                
                scope.$on('listenEvent', function(event, data) {
                	data = data.data;
                	scope.data = data;
                	$('.jOrgChart').remove();
                	drawOrgChart(data);
                });


                var items = [],
                    mCheck = [];
                var currentZoom = 100;
                var data = scope.data;
                var selectedStatus = scope.status;
                function statusSelectoin(status){
                    $('#statusclick li').removeClass('active');
                    scope.getCall()(status);
                }

                function drawOrgChart(data, supervisorOrg) {
                    items = [];
                    var itemHtml = "";
                    for(var i=0; i<data.length; i++){
                        loops(data[i]);
                    }
                    function loops(root) {
                        var className;
                        if (root.parent == "parent") {
                            items.push("<li class='root '>" +
                                "<div><div class='label_node' style='position:relative'>" +
                                "<span class='' style='position:absolute; left:1px; top:5px'>"+
                                "<div class='dropdown dd_"+root.id+"'>"+
                                    "<div style='float: right;'>"+
                                    "<button id='dd_"+root.id+"' class='btn btn-default dropdown-toggle icon-misc-filter' type='button' data-toggle='dropdown'>"+
                                    "</button>"+
                                    "<ul class='dropdown-menu' id='statusclick'>"+
                                      "<li id='active'>Active</li>"+
                                      "<li id='cancelled'>Cancelled</li>"+
                                      "<li id='terminated'>Terminated</li>"+
                                      "<li id='failed'>Failed</li>"+
                                    "</ul>"+
                                    "</div>"+
                                    "</div>"+
                                "</span>"+
                                "<span style='position: absolute; font-size: 12px; left:30px; top:10px' class='title-span'>" + root.blueprint_id + "</span>"+
                                            "<button id='dd_"+root.id+"' style='margin-top: 5px; position: absolute; right: 2px;' class='openmodal btn btn-default dropdown-toggle icon-controls-gear' " +
                                            "type='button' data-toggle='dropdown'>"+
                                            "</button>"+
                                "</div></div></br>"+
                                "<div style='padding-top:25px'><img src='app/oom/images/ecomp-login-550x360.jpg' width='180' height='100'></div>"+
                                "<div class='expandBtn'><span id='exp_"+root.id+"'><i class='icon-controls-add-maximize'></i></span></div><ul>");
                        } else {
                            itemHtml = "<li class='child'>"+
                            "<div style='padding-top:3px'><span class='label_node'>"+
                           
                            "<span class='title-span'>" + root.blueprint_id + "</span>"+
                            "<span class='icon-span ddIcon'>"+
                                    "<div class='dropdown dd_"+root.id+"'>"+
                                        "<div style='float: right; margin-top: -14px;'>"+
                                        "<button id='dd_"+root.id+"' class='btn btn-default dropdown-toggle' type='button' data-toggle='dropdown'><i class='icon-people-preview'></i>"+
                                        "<span id='dd_"+root.id+"'></span></button>"+
                                        "<ul class='dropdown-menu'>"+
                                          "<li><a tabindex='-1' href='javascript:void(0);'>Execution Id: " + root.id + "</a></li>"+
                                          "<li><a tabindex='-1' href='javascript:void(0);'>Health Check Status: </a></li>"+
                                          "<li><a tabindex='-1' href='javascript:void(0);'>Please click here for more information </a></li>"+                                        
                                        "</ul>"+
                                        "</div>"+
                                  "</div>"+
                            "</span>"+
                            "</span></div></br>"+
                            "<div style='padding-top:35px'><img src='app/oom/images/ecomp.png' width='180' height='100'></div></li>";
                            items.push(itemHtml);
                        }
                    } // End the generate html code

                    items.push('</ul></li>');
                    var listElem = $("<ul/>", {
                        "class": "org",
                        "style": "float:right;",
                        html: items.join("")
                    }).appendTo(elem);

                    var opts = {
                        chartElement: elem,
                        rowcolor: false
                    };
                    //elem.html('');
                    $(elem).find(".org").jOrgChart(opts);
                    listElem.remove();
                    
                    setTimeout(function(){
                        $('.dropdown .dropdown-toggle').on("click", function(e){
                            var cls = '.'+ e.target.id;
                            var subcls = cls + ' a.test';
                            if($(cls).hasClass('open')){
                                $(cls).removeClass('open');
                                $(subcls).next('ul').css({display: 'none'});
                            } else{
                                $(cls).addClass('open');
                            }
                        });

                        $('.dropdown-submenu a.test').on("mouseover", function(e){
                            console.log("hi");
                            $('#'+e.target.id).next('ul').toggle();
                        });

                        $('#statusclick li').click(function(event){
                            statusSelectoin(event.target.id);
                        })
                        
                        $('.openmodal').click(function(event){
                        	openModal();
                        })
                        
                        $(document).on('click','.expandBtn', function(event, data) {
                        	event.stopImmediatePropagation()
                            var $this = $(this);
                            var $tr = $this.closest("tr");
                            if ($tr.hasClass('contracted')) {
                                $(this).addClass('fa-minus').removeClass('fa-plus');
                                $tr.removeClass('contracted').addClass('expanded');
                                $tr.nextAll("tr").css('visibility', '');
                            } else {
                                $(this).addClass('fa-plus').removeClass('fa-minus');
                                $tr.removeClass('expanded').addClass('contracted');
                                $tr.nextAll("tr").css('visibility', 'hidden');
                            }
                        });
                        
                        var selectedStatus = scope.status;
                        if(selectedStatus){
                        	$('#'+selectedStatus).addClass('active');
                        }
                        
                        function openModal(){
                        	scope.modalCall()();
                        }

                    },0);
                }
                                	
               drawOrgChart(data);

            }
        };
    };
})();