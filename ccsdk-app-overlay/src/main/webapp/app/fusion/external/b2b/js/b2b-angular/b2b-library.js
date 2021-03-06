angular.module("b2b.att.tpls", ['b2bTemplate/audioPlayer/audioPlayer.html', 'b2bTemplate/audioRecorder/audioRecorder.html', 'b2bTemplate/backToTop/backToTop.html', 'b2bTemplate/boardstrip/b2bAddBoard.html', 'b2bTemplate/boardstrip/b2bBoard.html', 'b2bTemplate/boardstrip/b2bBoardstrip.html', 'b2bTemplate/calendar/datepicker-popup.html', 'b2bTemplate/calendar/datepicker.html', 'b2bTemplate/coachmark/coachmark.html', 'b2bTemplate/dropdowns/b2bDropdownDesktop.html', 'b2bTemplate/dropdowns/b2bDropdownGroupDesktop.html', 'b2bTemplate/dropdowns/b2bDropdownListDesktop.html', 'b2bTemplate/fileUpload/fileUpload.html', 'b2bTemplate/filmstrip/b2bFilmstrip.html', 'b2bTemplate/filmstrip/b2bFilmstripContent.html', 'b2bTemplate/flyout/flyout.html', 'b2bTemplate/flyout/flyoutContent.html', 'b2bTemplate/footer/footer_column_switch_tpl.html', 'b2bTemplate/horizontalTable/horizontalTable.html', 'b2bTemplate/hourPicker/b2bHourpicker.html', 'b2bTemplate/hourPicker/b2bHourpickerPanel.html', 'b2bTemplate/hourPicker/b2bHourpickerValue.html', 'b2bTemplate/leftNavigation/leftNavigation.html', 'b2bTemplate/listbox/listbox.html', 'b2bTemplate/modalsAndAlerts/b2b-backdrop.html', 'b2bTemplate/modalsAndAlerts/b2b-window.html', 'b2bTemplate/monthSelector/monthSelector-popup.html', 'b2bTemplate/monthSelector/monthSelector.html', 'b2bTemplate/monthSelector/monthSelectorLink.html', 'b2bTemplate/pagination/b2b-pagination.html', 'b2bTemplate/paneSelector/paneSelector.html', 'b2bTemplate/paneSelector/paneSelectorPane.html', 'b2bTemplate/profileCard/profileCard-addUser.html', 'b2bTemplate/profileCard/profileCard.html', 'b2bTemplate/reorderList/reorderList.html', 'b2bTemplate/searchField/searchField.html', 'b2bTemplate/seekBar/seekBar.html', 'b2bTemplate/slider/slider.html', 'b2bTemplate/spinButton/spinButton.html', 'b2bTemplate/statusTracker/statusTracker.html', 'b2bTemplate/stepTracker/stepTracker.html', 'b2bTemplate/switches/switches-spanish.html', 'b2bTemplate/switches/switches-v2.html', 'b2bTemplate/switches/switches.html', 'b2bTemplate/tableMessages/tableMessage.html', 'b2bTemplate/tableScrollbar/tableScrollbar.html', 'b2bTemplate/tables/b2bResponsiveRow.html', 'b2bTemplate/tables/b2bTable.html', 'b2bTemplate/tables/b2bTableBody.html', 'b2bTemplate/tables/b2bTableHeaderSortable.html', 'b2bTemplate/tables/b2bTableHeaderUnsortable.html', 'b2bTemplate/tabs/b2bTab.html', 'b2bTemplate/tabs/b2bTabset.html', 'b2bTemplate/treeNav/groupedTree.html', 'b2bTemplate/treeNav/treeMember.html', 'b2bTemplate/treeNav/ungroupedTree.html', 'b2bTemplate/treeNodeCheckbox/groupedTree.html', 'b2bTemplate/treeNodeCheckbox/treeMember.html', 'b2bTemplate/treeNodeCheckbox/ungroupedTree.html', 'b2bTemplate/usageBar/usageBar.html']);angular.module("b2b.att", ["b2b.att.tpls", 'b2b.att.addressInputTemplate','b2b.att.arrows','b2b.att.audioPlayer','b2b.att.audioRecorder','b2b.att.backToTop','b2b.att.badgesForAlerts','b2b.att.bellybandLinks','b2b.att.boardstrip','b2b.att.bootstrapGridTemplate','b2b.att.breadcrumbs','b2b.att.buttonGroups','b2b.att.buttons','b2b.att.calendar','b2b.att.cards','b2b.att.checkboxes','b2b.att.coachmark','b2b.att.configurationSection','b2b.att.directoryListingTemplate','b2b.att.dropdowns','b2b.att.fileUpload','b2b.att.filmstrip','b2b.att.filters','b2b.att.flyout','b2b.att.footer','b2b.att.header','b2b.att.headingsAndCopy','b2b.att.horizontalTable','b2b.att.hourPicker','b2b.att.inputTemplate','b2b.att.leftNavigation','b2b.att.links','b2b.att.listbox','b2b.att.loaderAnimation','b2b.att.messageWrapper','b2b.att.modalsAndAlerts','b2b.att.monthSelector','b2b.att.multiLevelNavigation','b2b.att.multipurposeExpander','b2b.att.notesMessagesAndErrors','b2b.att.notificationCardTemplate','b2b.att.orderConfirmationTemplate','b2b.att.pagination','b2b.att.paneSelector','b2b.att.phoneNumberInput','b2b.att.profileBlockTemplate','b2b.att.profileCard','b2b.att.radios','b2b.att.reorderList','b2b.att.searchField','b2b.att.seekBar','b2b.att.separators','b2b.att.slider','b2b.att.spinButton','b2b.att.staticRouteTemplate','b2b.att.statusTracker','b2b.att.stepTracker','b2b.att.switches','b2b.att.switchesv2','b2b.att.tableDragAndDrop','b2b.att.tableMessages','b2b.att.tableScrollbar','b2b.att.tables','b2b.att.tabs','b2b.att.tagBadges','b2b.att.textArea','b2b.att.timeInputField','b2b.att.tooltipsForForms','b2b.att.treeNav','b2b.att.treeNodeCheckbox','b2b.att.usageBar','b2b.att.utilities']);/**
 * @ngdoc directive
 * @name Template.att:Address Input
 *
 * @description
 *  <file src="src/addressInputTemplate/docs/readme.md" />
 *
 * @usage

 *
 * @example
 *  <section id="code">   
 <example module="b2b.att">
 <file src="src/addressInputTemplate/docs/demo.html" />
 <file src="src/addressInputTemplate/docs/demo.js" />
 </example>
 </section>
 *
 */
angular.module('b2b.att.addressInputTemplate', ['ngMessages']);
/**
 * @ngdoc directive
 * @name Buttons, links & UI controls.att:arrows
 *
 * @description
 *  <file src="src/arrows/docs/readme.md" />
 *
 * @usage
 *   Please refer demo.html tab in Example section below.
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/arrows/docs/demo.html" />
            <file src="src/arrows/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.arrows', []);
/**
 * @ngdoc directive
 * @name Videos, audio & animation.att:Audio Player
 * @scope
 * @param {string} audioSrcUrl - MP3 audio source URL or Blob URL
 * @description
 *  <file src="src/audioPlayer/docs/readme.md" />
 *
 * @usage
 * 
 <div b2b-audio audio-src-url='audioSrcUrl'></div>
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/audioPlayer/docs/demo.html" />
            <file src="src/audioPlayer/docs/demo.js" />
       </example>
    </section>
 *
 */
 
angular.module('b2b.att.audioPlayer', ['b2b.att.utilities', 'b2b.att.slider'])
	.constant('AudioPlayerConfig', {
		'defaultVolume': 50,
		'timeShiftInSeconds': 5
	})
	.filter('trustedAudioUrl', ['$sce', function ($sce) {
		return function (audioFileFullPath) {
			return audioFileFullPath ? $sce.trustAsResourceUrl(audioFileFullPath) : 'undefined';
		};
	}])
	.directive('b2bAudio', ['$log', '$timeout', 'AudioPlayerConfig', '$compile', 'events', function ($log, $timeout, AudioPlayerConfig, $compile, events) {
		return {
			restrict: 'EA',
			replace: true,
			scope: {
				audioSrcUrl: '=',
				disabled: '=',
				audioConfiguration: '=?'
			},
			templateUrl: 'b2bTemplate/audioPlayer/audioPlayer.html',
			controller: function ($scope) {
				$scope.audio = {};
				if (!angular.isDefined($scope.audioConfiguration)) {
					$scope.audioConfiguration = {
						'playLabel' : 'play',
        				'pauseLabel' : 'pause'
					}
				}
				if (!angular.isDefined($scope.audioSrcUrl)) {
					$log.warn('b2b-audio : audio-src-url undefined');
					$scope.audioSrcUrl = undefined;
					$scope.audio.mp3 = undefined;
				}
				
			},
			link: function (scope, element, attr) {
				var audioElement = angular.element(element[0].querySelector('audio'))[0];
				var audioSrcElement = angular.element(element[0].querySelector('audio source'))[0];
				scope.audio.audioElement = audioElement;
				var infinityDuration = false;
				element[0].removeAttribute("disabled");
				function setAttributes(element, attributes) {
					Object.keys(attributes).forEach(function (name) {
						element.setAttribute(name, attributes[name]);
					});
				}

				$timeout(function () {
					// TODO: Replace with DDA Tooltip
					var seekBarKnob = element[0].querySelector('.slider-knob');
					var tooltipObject = {
						'tooltip': '{{timeFormatter(audio.currentTime)}}',
						'tooltip-placement': 'above',
						'tooltip-style': 'blue',
						'tooltip-trigger': 'mousedown',
						'tooltip-append-to-body': 'false',
						'tooltip-offset': '-10',
						'refer-by': 'seek-bar-tooltip'
					};
					setAttributes(seekBarKnob, tooltipObject);
					$compile(seekBarKnob)(scope);
				});

				if (angular.isDefined(scope.audioSrcUrl)) {
					scope.audio.mp3 = scope.audioSrcUrl;
				}

				scope.audio.currentTime = 0;
				scope.audio.currentVolume = AudioPlayerConfig.defaultVolume;
				scope.audio.timeShiftInSeconds = AudioPlayerConfig.timeShiftInSeconds;
				scope.isPlayInProgress = false;
				scope.isReady = false;
				scope.isAudioDragging = false;
				scope.audioError = false;
				$timeout(function () {
					audioElement.load();
					audioElement.volume = scope.audio.currentVolume / 100;
				});

				scope.$watch('audioSrcUrl', function (newVal, oldVal) {
					if (newVal !== oldVal) {
						if (!newVal) {
							$log.warn('b2b-audio : audio-src-url undefined. Please provide a valid URL');
						}
						
						scope.audio.mp3 = newVal;
						$timeout(function () {
							audioElement.load();
						});
					}
				});
				
				scope.$watch('disabled', function (newVal, oldVal) {
					if(newVal){
						if(scope.disabled){
							scope.pauseAudio();
						}
						element.addClass("b2b-audio-disabled");
						angular.element(element[0].querySelector(".controls-wrapper")).attr({'tabindex':-1, 'aria-disabled':true})
						angular.element(element[0].querySelector(".audio-volume-control")).attr({'tabindex':-1, 'aria-disabled':true}) 
					}else{
						element.removeClass("b2b-audio-disabled");
						angular.element(element[0].querySelector(".controls-wrapper")).attr({'tabindex':0, 'aria-disabled':false})
						angular.element(element[0].querySelector(".audio-volume-control")).attr({'tabindex':0, 'aria-disabled':false})  
					}
				});


				scope.playAudio = function () {
					if (scope.isReady) {
						audioElement.play();
					}
				};

				audioElement.onplay = function () {
					scope.isPlayInProgress = true;
					scope.$digest();
				};

				scope.pauseAudio = function () {
					audioElement.pause();
				};

				audioElement.onpause = function () {
					scope.isPlayInProgress = false;
					scope.$digest();
				};
				audioElement.onerror = function(){
					scope.audioError = true;
					var fileName = scope.audio.mp3.split("/");
					scope.audioFileName = fileName[fileName.length-1];
				};
				audioSrcElement.onerror = function(){
					scope.audioError = true;
					var fileName = scope.audio.mp3.split("/");
					scope.audioFileName = fileName[fileName.length-1];
				};
				scope.volumeControl = function() {
					if(element[0].querySelector('.b2b-audio-popover .slider-knob')){
						$timeout(function () {
							element[0].querySelector('.b2b-audio-popover .slider-knob').focus();
						},500);
					}
				}
				scope.toggleAudio = function () {
					if (audioElement.paused) {
						scope.playAudio();
					} else {
						scope.pauseAudio();
					}
				};

				scope.volumeUp = function (delta) {
					if (!delta) {
						delta = 0.1;
					} else {
						delta = delta / 100;
					}
					audioElement.muted = false;
					if (audioElement.volume < 1) {
						audioElement.volume = Math.min((Math.round((audioElement.volume + delta) * 100) / 100), 1);
					}
					scope.audio.currentVolume = audioElement.volume * 100;
					return audioElement.volume;
				};

				scope.volumeDown = function (delta) {
					if (!delta) {
						delta = 0.1;
					} else {
						delta = delta / 100;
					}
					audioElement.muted = false;
					if (audioElement.volume > 0) {
						audioElement.volume = Math.max((Math.round((audioElement.volume - delta) * 100) / 100), 0);
					}
					scope.audio.currentVolume = audioElement.volume * 100;
					return audioElement.volume;
				};

				var volumeHandler = function (e) {
					events.preventDefault(e);
					if ((e.wheelDelta && e.wheelDelta > 0) || (e.detail && e.detail < 0)) {
						scope.volumeUp();
					} else {
						scope.volumeDown();
					}
					scope.$digest();
				};

				

				scope.$watch('audio.currentVolume', function (newVal, oldVal) {
					if (newVal !== oldVal) {
						audioElement.volume = newVal / 100;
					}
				});

				scope.setCurrentTime = function (timeInSec) {
					audioElement.currentTime = timeInSec;
				};

				scope.setAudioPosition = function (val) {
					if (scope.isReady) {
						scope.setCurrentTime(val);
						scope.isAudioDragging = false;
					}
				};

				function getTimestampArray(timestamp) {
					var d = Math.abs(timestamp) / 1000; // delta
					var r = {}; // result
					var s = { // structure
						day: 86400,
						hour: 3600,
						minute: 60,
						second: 1
					};

					Object.keys(s).forEach(function (key) {
						r[key] = Math.floor(d / s[key]);
						d -= r[key] * s[key];
					});

					return r;
				};

				scope.timeFormatter = function (timeInSec) {
					var formattedTime = '00:00';

					if (!timeInSec || timeInSec < 1) {
						return formattedTime;
					}

					if (typeof timeInSec === 'string') {
						return timeInSec;
					}

					var dateArray = getTimestampArray(timeInSec * 1000);
					Object.keys(dateArray).forEach(function (key) {
						if (dateArray[key] === 0) {
							dateArray[key] = '00';
						} else if (dateArray[key] < 10) {
							dateArray[key] = '0' + dateArray[key];
						}
					});

					formattedTime = dateArray['minute'] + ':' + dateArray['second'];

					if (dateArray['hour'] !== '00') {
						formattedTime = dateArray['hour'] + ':' + formattedTime;
					}

					if (dateArray['day'] !== '00') {
						formattedTime = dateArray['day'] + ':' + formattedTime;
					}

					return formattedTime;
				};

				audioElement.onloadedmetadata = function () {
					/* Few of the audio files were returning the duration as Infinity, hence moving the current time by 180 sec and the resetting the current time to zero*/
					if(audioElement.duration === Infinity){
						infinityDuration = true;
						audioElement.currentTime = 360;
					}else{
						scope.audio.duration = audioElement.duration;
						scope.$digest();
					}
				};

				audioElement.ontimeupdate = function () {
					if(infinityDuration === true && audioElement.duration !== Infinity){
						audioElement.currentTime = 0;
						infinityDuration = false;
						scope.audio.duration = audioElement.duration;
						scope.$digest();
					}
					if (!scope.isAudioDragging) {
						scope.audio.currentTime = audioElement.currentTime;
						scope.$digest();
					}
				};

				audioElement.onended = function () {
					scope.setCurrentTime(0);
					scope.audio.currentTime = 0;
					if (!audioElement.paused) {
						scope.pauseAudio();
					}
					scope.$digest();
				};

				audioElement.oncanplay = function () {
					scope.isReady = true;
					scope.isPlayInProgress = !audioElement.paused;
					scope.$digest();
				};

				var onloadstart = function () {
					scope.isReady = false;
					scope.isPlayInProgress = !audioElement.paused;
					scope.audio.currentTime = 0;
					scope.audio.duration = 0;
					scope.$digest();
				};
				audioElement.addEventListener("loadstart", onloadstart);
			}
		};
	}]);
/**
 * @ngdoc directive
 * @name Videos, audio & animation.att:Audio Recorder
 * @scope
 * @param {function} callback - A callback to handle the WAV blob
 * @param {object} config - A config object with properties startRecordingMessage & whileRecordingMessage
 * @description
 *  <file src="src/audioRecorder/docs/readme.md" />
 *
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/audioRecorder/docs/demo.html" />
            <file src="src/audioRecorder/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.audioRecorder', ['b2b.att.utilities'])
    .constant('AudioRecorderConfig', {
        'startRecordingMessage': 'Click on REC icon to begin recording',
        'whileRecordingMessage': 'Recording...',
        'stopLabel': 'stop',
        'recordingLabel': 'record'
    })
    .directive('b2bAudioRecorder', ['$interval', 'AudioRecorderConfig', 'b2bUserAgent', 'b2bRecorder', function($interval, AudioRecorderConfig, b2bUserAgent, b2bRecorder) {
        return {
            restrict: 'EA',
            replace: true,
            scope: {
            	callback: '&',
                audioRecorderConfiguration: '=?'
            },
            templateUrl: 'b2bTemplate/audioRecorder/audioRecorder.html',
            controller: function($scope) {

                function hasGetUserMedia() {
                    return !!(navigator.getUserMedia || navigator.webkitGetUserMedia ||
                        navigator.mozGetUserMedia || navigator.msGetUserMedia);
                }

                if (!hasGetUserMedia()) {
                    throw new Error('Your broswer does not support MediaRecorder API');
                }

                if (!(b2bUserAgent.isFF() || b2bUserAgent.isChrome())) {
					throw new Error('b2bAudioRecorder does not support this browser!');
				}

            },
            link: function(scope, element) {
                scope.elapsedTime = 0;
                scope.isRecording = false;
                scope.config = {};
                if (!angular.isDefined(scope.audioRecorderConfiguration)) {
                    scope.config.startRecordingMessage = AudioRecorderConfig.startRecordingMessage;
                    scope.config.whileRecordingMessage = AudioRecorderConfig.whileRecordingMessage;
                    scope.config.recordingLabel = AudioRecorderConfig.recordingLabel;
                    scope.config.stopLabel = AudioRecorderConfig.stopLabel;
                }else{
                    scope.config = scope.audioRecorderConfiguration;
                }
                

                var timer = undefined; // Interval promise
                navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
                var stream;
                var audio = angular.element(element[0].querySelector('audio'))[0];
                var recorder = undefined;
                var audioStream;
                function startRecording() {
                    scope.isRecording = true;
                    navigator.mediaDevices.getUserMedia({
                        audio: true
                    }).then(function(stream) {
                        //create the MediaStreamAudioSourceNode
                        audioStream = stream;
                        context = new AudioContext();
                        source = context.createMediaStreamSource(stream);
                        recorder = new b2bRecorder(source);
                        recorder.record();

                        timer = $interval(function() {
                            scope.elapsedTime += 1;
                        }, 1000, 0);
                    }).catch(function(err) {
                        angular.noop();
                    });

                };

                function stopRecording() {
                    scope.isRecording = false;
                    recorder.stop();
                    var audio = {};
                    recorder.exportWAV(function(s) {
                        audio.src = window.URL.createObjectURL(s);
                        context.close().then(function() {
	                    	if (timer) {
		                        $interval.cancel(timer);
		                    }
		                    scope.elapsedTime = 0;
		                    
		                    recorder.clear();
		                    recorder = undefined;
	                    });
	                    if (angular.isFunction(scope.callback)){
							scope.callback({'data': audio});	
						}
                    });
                    if (angular.isFunction(audioStream.stop)){
                        audioStream.stop();
                    }
                    var tracks = audioStream.getTracks();
                    for(var i=0; i<tracks.length; i++){
                        tracks[i].stop();
                    }
                    
                }

                scope.toggleRecording = function() {
                    if (scope.isRecording) {
                        stopRecording();
                    } else {
                        startRecording();
                    }
                };



                //TODO: Move this into utilities
                function getTimestampArray(timestamp) {
                    var d = Math.abs(timestamp) / 1000; // delta
                    var r = {}; // result
                    var s = { // structure
                        day: 86400,
                        hour: 3600,
                        minute: 60,
                        second: 1
                    };

                    Object.keys(s).forEach(function(key) {
                        r[key] = Math.floor(d / s[key]);
                        d -= r[key] * s[key];
                    });

                    return r;
                };
                scope.timeFormatter = function(timeInSec) {
                    var formattedTime = '00:00';

                    if (!timeInSec || timeInSec < 1) {
                        return formattedTime;
                    }

                    if (typeof timeInSec === 'string') {
                        return timeInSec;
                    }

                    var dateArray = getTimestampArray(timeInSec * 1000);
                    Object.keys(dateArray).forEach(function(key) {
                        if (dateArray[key] === 0) {
                            dateArray[key] = '00';
                        } else if (dateArray[key] < 10) {
                            dateArray[key] = '0' + dateArray[key];
                        }
                    });

                    formattedTime = dateArray['minute'] + ':' + dateArray['second'];

                    if (dateArray['hour'] !== '00') {
                        formattedTime = dateArray['hour'] + ':' + formattedTime;
                    }

                    if (dateArray['day'] !== '00') {
                        formattedTime = dateArray['day'] + ':' + formattedTime;
                    }

                    return formattedTime;
                };

                scope.$on('$destroy', function() {
                    if (timer) {
                        $interval.cancel(timer);
                    }
                });
            }
        };
    }]);

/**
 * @ngdoc directive
 * @name Navigation.att:Back To Top
 * @scope
 * @description
 *  <file src="src/backToTop/docs/readme.md" />
 * @param {integer} scrollSpeed - Scroll speed in seconds, default is 1
*
 * @usage
 * 
 	<div ng-controller="backToTopController">
 	 	<div b2b-backtotop></div>
 	</div>
 * 
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/backToTop/docs/demo.html" />
            <file src="src/backToTop/docs/demo.js" />
       </example>
    </section>
 *
 */
 
angular.module('b2b.att.backToTop', ['b2b.att.utilities','b2b.att.position'])
	.directive('b2bBacktotopButton', [function () {
		return {
			restrict: 'EA',
			replace: true,
			templateUrl: 'b2bTemplate/backToTop/backToTop.html',
			link: function (scope, elem, attr) {
				elem.bind('click', function(evt) {
					var scrollSpeed = parseInt(attr.scrollSpeed) || 1;
                    TweenLite.to(window, scrollSpeed, {scrollTo:{x: 0, y: 0}});
                });
			}
		};
	}]);
/**
 * @ngdoc directive
 * @name Messages, modals & alerts.att:badgesForAlerts
 *
 * @description
 *  <file src="src/badgesForAlerts/docs/readme.md" />
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/badgesForAlerts/docs/demo.html" />
            <file src="src/badgesForAlerts/docs/demo.js" />
       </example>
        </section>
 *
 */
angular.module('b2b.att.badgesForAlerts', []);
/**
 * @ngdoc directive
 * @name Misc.att:bellybandLinks
 *
 * @description
 *  <file src="src/bellybandLinks/docs/readme.md" />
 *
 * @usage
 *   <div class="b2b-bellyband-container autoSize" b2b-bellyband-autosize-what=".b2b-bellyband-link">
            <div class="b2b-bellyband-group span12 span6-sm span12-xsm">
                <div class="span4 span12-sm b2b-bellyband-link">
                    <a href="patterns.html">
                        <i class="icon-misc-puzzleL visible-desktop"></i>
                        <i class="icon-misc-puzzle hidden-desktop"></i>
                        <div>
                            <span>Pattern library</span>
                            <p class="hidden-phone">Review our digital design standards and patterns for creating AT&amp;T digital assets. </p>
                        </div>
                    </a>
                </div>
                <div class="span4 span12-sm b2b-bellyband-link">
                    <a href="support-development-overview.html">
                        <i class="icon-datanetwork-cloudL visible-desktop"></i>
                        <i class="icon-datanetwork-cloud hidden-desktop"></i>
                        <div>
                            <span>Development overview</span>
                            <p class="hidden-phone">Set up your environment, access the code snippets, and follow our standards for creating a digital code base.</p>
                        </div>
                    </a>
                </div>
                <div class="span4 span12-sm b2b-bellyband-link">
                    <a href="getstarted-design-principles.html">
                        <i class="icon-misc-bulbL visible-desktop"></i>
                        <i class="icon-misc-bulb hidden-desktop"></i>
                        <div>
                            <span>Design principles</span>
                            <p class="hidden-phone">Great design is built on an even greater foundation. Learn the ins and outs of our design philosophy.</p>
                        </div>
                    </a>
                </div>
            </div>
        </div>
 
 * @example
 *  <section id="code">   
 <b>HTML + AngularJS</b>
 <example module="b2b.att">
 <file src="src/bellybandLinks/docs/demo.html" />
 <file src="src/bellybandLinks/docs/demo.js" />
 </example>
 </section>
 *
 */
angular.module('b2b.att.bellybandLinks', [])
        .directive('b2bBellybandAutosizeWhat', ['$window', function($window) {
                return {
                    restrict: 'EA',
                    link: function(scope, ele, attr) {
                        var bellyBandGroupElement;
                        var sizeWhat = attr.bellybandAutosizeWhat;
                        var haveBellybandGgroupClass = ele.hasClass('b2b-bellyband-group');

                        if (haveBellybandGgroupClass) {
                            bellyBandGroupElement = ele[0];
                        }
                        else {
                            bellyBandGroupElement = ele[0].querySelectorAll('.b2b-bellyband-group');
                        }

                        var bellyBandCssCalculation = function() {
                            angular.forEach(ele[0].querySelectorAll('.b2b-bellyband-link'), function(value, key) {
                                var newElement = angular.element(value);
                                if ($window.innerWidth <= '479') {
                                    newElement.removeAttr('style');
                                    angular.element(bellyBandGroupElement).removeAttr('style');
                                }
                                if ($window.innerWidth > '479' && $window.innerWidth < '768') {

                                    var lnkHeight = getComputedStyle(value, null).height.split("px");

                                    lnkMar = getComputedStyle(value, null).marginBottom.split("px");

                                    var bblink;

                                    if (ele[0].querySelectorAll('.b2b-bellyband-link').length === 2) {
                                        bblink = ((parseInt(lnkHeight[0]) + parseInt(lnkMar[0])) * 1) + 'px';
                                    }

                                    else if ((ele[0].querySelectorAll('.b2b-bellyband-link').length === 3) || (ele[0].querySelectorAll('.b2b-bellyband-link').length === 4)){
                                        bblink = ((parseInt(lnkHeight[0]) + parseInt(lnkMar[0])) * 2) + 'px';
                                    }

                                    else if (ele[0].querySelectorAll('.b2b-bellyband-link').length >= 5) {
                                        bblink = ((parseInt(lnkHeight[0]) + parseInt(lnkMar[0])) * 3) + 'px';
                                    }

                                    angular.element(bellyBandGroupElement).css({
                                        'max-height': bblink
                                    });

                                } else if ($window.innerWidth >= '768') {
                                    
                                   for(var i = 0;i<angular.element(bellyBandGroupElement).length;i++){
                                       
                                        angular.element(bellyBandGroupElement[i]).css({
                                        'max-height': 'inherit'
                                    });
                                       
                                   }
                                    if (angular.element(bellyBandGroupElement).length && angular.element(bellyBandGroupElement)[0].querySelectorAll('.b2b-bellyband-link').length === 6) {
                                        newElement.css(
                                                {'flex': '1 1 0'}
                                        );
                                    }
                                }
                            });
                        };

                        function tallestSize() {

                            var maxInnerHeight = -1;
                            angular.forEach(ele[0].querySelectorAll(sizeWhat), function(value, key) {
                                if (maxInnerHeight < value.clientHeight) {
                                    maxInnerHeight = value.clientHeight;
                                }
                            });
                            return maxInnerHeight;
                        }
                        var setautoSize = function() {
                            var autoSize2 = tallestSize() + "px";
                            angular.forEach(ele[0].querySelectorAll('.autoSize .b2b-bellyband-link'), function(value, key) {
                                var newElement = angular.element(value);
                                newElement.css({
                                    'height': autoSize2
                                });
                            });
                        };
                        // setautoSize();
                        bellyBandCssCalculation();

                        angular.element($window).bind('resize', function() {
                            angular.element(ele[0].querySelectorAll('.b2b-bellyband-link')).removeAttr('style');
                            // setautoSize();
                            bellyBandCssCalculation();
                        });
                    }
                };
            }]);
/**
 * @ngdoc directive
 * @name Misc.att:boardstrip
 *
 * @description
 *  <file src="src/boardstrip/docs/readme.md" />
 *
 * @usage
 * See demo section
 * @example
    <section id="code">
        <b>HTML + AngularJS</b>
        <example module="b2b.att">
            <file src="src/boardstrip/docs/demo.html" />
            <file src="src/boardstrip/docs/demo.js" />
        </example>
    </section>
 */
angular.module('b2b.att.boardstrip', ['b2b.att.utilities'])
	.constant('BoardStripConfig', {
		'maxVisibleBoards': 4,
		'boardsToScroll': 1,
		/* These parameters are non-configurable and remain unaltered, until there is a change in corresponding CSS */
	    'boardLength': 140,
		'boardMargin': 15
	})
	.directive('b2bBoard', [function () {
		return {
			restrict: 'AE',
			replace: true,
			transclude: true,
			require: '^b2bBoardStrip',
			scope: {
				boardIndex: '=',
				boardLabel: '='
			},
			templateUrl: 'b2bTemplate/boardstrip/b2bBoard.html',
			link: function (scope, element, attrs, ctrls) {

				var parentCtrl = ctrls;

				scope.getCurrentIndex = function () {
					return parentCtrl.getCurrentIndex();
				};
				scope.selectBoard = function (boardIndex) {
					if (!isNaN(boardIndex)) {
						parentCtrl.setCurrentIndex(boardIndex);
					}
				};
			}
		};
	}])
	.directive('b2bBoardStrip', ['BoardStripConfig', '$timeout', function (BoardStripConfig, $timeout) {
		return {
			restrict: 'AE',
			replace: true,
			transclude: true,
			require: ['?ngModel', 'b2bBoardStrip'],
			scope: {
				boardsMasterArray: '=',
				onAddBoard: '&?'
			},
			templateUrl: 'b2bTemplate/boardstrip/b2bBoardstrip.html',
			controller: function ($scope) {
				if (!angular.isDefined($scope.boardsMasterArray)) {
					$scope.boardsMasterArray = [];
				}

				this.rectifyMaxVisibleBoards = function () {
					if (this.maxVisibleIndex >= $scope.boardsMasterArray.length) {
						this.maxVisibleIndex = $scope.boardsMasterArray.length - 1;
					}

					if (this.maxVisibleIndex < 0) {
						this.maxVisibleIndex = 0;
					}
				};

				this.resetBoardStrip = function () {
					$scope.currentIndex = 0;

					this.maxVisibleIndex = BoardStripConfig.maxVisibleBoards - 1;
					this.minVisibleIndex = 0;

					this.rectifyMaxVisibleBoards();
				};

				this.getCurrentIndex = function () {
					return $scope.currentIndex;
				};
				this.setCurrentIndex = function (indx) {
					$scope.currentIndex = indx;
				};

				this.getBoardsMasterArrayLength = function () {
					return $scope.boardsMasterArray.length;
				};

				$scope.addBoardPressedFlag = false;
				this.getAddBoardPressedFlag = function () {
					return $scope.addBoardPressedFlag;
				};
				this.setAddBoardPressedFlag = function (booleanValue) {
					$scope.addBoardPressedFlag = booleanValue;
				};

			},
			link: function (scope, element, attrs, ctrls) {

				var ngModelCtrl = ctrls[0];
				var ctrl = ctrls[1];

				var oldTimeout;
				var animationTimeout = 1000;

				var getBoardViewportWidth = function (numberOfVisibleBoards) {
					return numberOfVisibleBoards * (BoardStripConfig.boardLength + BoardStripConfig.boardMargin);
				};
				if (element[0].querySelector(".board-viewport")) {
					angular.element(element[0].querySelector(".board-viewport")).css({
						"width": getBoardViewportWidth(BoardStripConfig.maxVisibleBoards) + "px"
					});
				}

				var getBoardstripContainerWidth = function (totalNumberOfBoards) {
					return totalNumberOfBoards * (BoardStripConfig.boardLength + BoardStripConfig.boardMargin);
				};
				if (element[0].querySelector(".boardstrip-container")) {
					angular.element(element[0].querySelector(".boardstrip-container")).css({
	                    "width": getBoardstripContainerWidth(ctrl.getBoardsMasterArrayLength()) + "px"
					});
					angular.element(element[0].querySelector(".boardstrip-container")).css({
						"left": "0px"
					});
				}

				var calculateAndGetBoardstripContainerAdjustment = function () {

					var calculatedAdjustmentValue;

					if (ctrl.getBoardsMasterArrayLength() <= BoardStripConfig.maxVisibleBoards) {
						calculatedAdjustmentValue = 0;
					} else {
						calculatedAdjustmentValue = (ctrl.minVisibleIndex * (BoardStripConfig.boardLength + BoardStripConfig.boardMargin)) * -1;
					}

					return calculatedAdjustmentValue;
				};

				var animateBoardstripContainerAdjustment = function (elementToFocusAfterAnimation) {
					var oldContainerAdjustment = angular.element(element[0].querySelector(".boardstrip-container"))[0].style.left;
					var containerAdjustment = calculateAndGetBoardstripContainerAdjustment();
					if (oldContainerAdjustment !== containerAdjustment + 'px') {
						angular.element(element[0].querySelector(".boardstrip-container")).css({
							"left": containerAdjustment + "px"
						});

						$timeout.cancel(oldTimeout);
						oldTimeout = $timeout(function () {
							elementToFocusAfterAnimation.focus();
						}, animationTimeout);
					} else {
						elementToFocusAfterAnimation.focus();
					}
				};

				var updateBoardsTabIndex = function (boardArray, minViewIndex, maxViewIndex) {
					for (var i = 0; i < boardArray.length; i++) {
						angular.element(boardArray[i]).attr('tabindex', '-1');
					}
					for (var j = minViewIndex; j <= maxViewIndex; j++) {
						angular.element(boardArray[j]).attr('tabindex', '0');
					}
				};

				$timeout(function () {
					var currentBoardArray = element[0].querySelectorAll('[b2b-board]');
					updateBoardsTabIndex(currentBoardArray, ctrl.minVisibleIndex, ctrl.maxVisibleIndex);
				});

				scope.$watchCollection('boardsMasterArray', function (newVal, oldVal) {
					if (newVal !== oldVal) {
						/* When a board is removed */
						if (newVal.length < oldVal.length) {
							ctrl.resetBoardStrip();
							$timeout(function () {

								var currentBoardArray = element[0].querySelectorAll('[b2b-board]');
								if (currentBoardArray.length !== 0) {
									animateBoardstripContainerAdjustment(currentBoardArray[0]);
								} else {
									element[0].querySelector('div.boardstrip-item--add').focus();
								}

								angular.element(element[0].querySelector(".boardstrip-container")).css({
									"width": getBoardstripContainerWidth(ctrl.getBoardsMasterArrayLength()) + "px"
								});
								/* Update tabindecies to ensure keyboard navigation behaves correctly */
								updateBoardsTabIndex(currentBoardArray, ctrl.minVisibleIndex, ctrl.maxVisibleIndex);
							});
						}
						/* When a board is added */
						else {
	                        if (ctrl.getAddBoardPressedFlag()) {
								ctrl.maxVisibleIndex = ctrl.getBoardsMasterArrayLength() - 1;
								ctrl.minVisibleIndex = Math.max(ctrl.maxVisibleIndex - BoardStripConfig.maxVisibleBoards + 1, 0);

								ctrl.setCurrentIndex(ctrl.maxVisibleIndex);

								$timeout(function () {
									angular.element(element[0].querySelector(".boardstrip-container")).css({
										"width": getBoardstripContainerWidth(ctrl.getBoardsMasterArrayLength()) + "px"
									});

									var currentBoardArray = element[0].querySelectorAll('[b2b-board]');
									animateBoardstripContainerAdjustment(currentBoardArray[currentBoardArray.length - 1]);
									/* Update tabindecies to ensure keyboard navigation behaves correctly */
									updateBoardsTabIndex(currentBoardArray, ctrl.minVisibleIndex, ctrl.maxVisibleIndex);
								});
	                        } else {
	                            if (ctrl.minVisibleIndex === 0 && ctrl.getBoardsMasterArrayLength() < BoardStripConfig.maxVisibleBoards + 1) {
	                                ctrl.maxVisibleIndex = ctrl.getBoardsMasterArrayLength() - 1;
	                                ctrl.rectifyMaxVisibleBoards();
	                            }
	
	                            $timeout(function () {
	                                angular.element(element[0].querySelector(".boardstrip-container")).css({
	                                    "width": getBoardstripContainerWidth(ctrl.getBoardsMasterArrayLength()) + "px"
	                                });
	
	                                var currentBoardArray = element[0].querySelectorAll('[b2b-board]');
	                                /* Update tabindecies to ensure keyboard navigation behaves correctly */
	                                updateBoardsTabIndex(currentBoardArray, ctrl.minVisibleIndex, ctrl.maxVisibleIndex);
	                            });
	                        }
	
							ctrl.setAddBoardPressedFlag(false);
						}
					}
				});

				scope.nextBoard = function () {
					ctrl.maxVisibleIndex += BoardStripConfig.boardsToScroll;
					ctrl.rectifyMaxVisibleBoards();
					ctrl.minVisibleIndex = ctrl.maxVisibleIndex - (BoardStripConfig.maxVisibleBoards - 1);

					$timeout.cancel(oldTimeout);
					angular.element(element[0].querySelector(".boardstrip-container")).css({
						"left": calculateAndGetBoardstripContainerAdjustment() + "px"
					});

					$timeout(function () {
						var currentBoardArray = element[0].querySelectorAll('[b2b-board]');

						/* Remove tabindex from non-visible boards */
						updateBoardsTabIndex(currentBoardArray, ctrl.minVisibleIndex, ctrl.maxVisibleIndex);

						if (!(scope.isNextBoard())) {
							try {
								currentBoardArray[currentBoardArray.length - 1].focus();
							} catch (e) { /* IE8 may throw exception */ }
						}
					}, animationTimeout);
				};
				scope.prevBoard = function () {

					ctrl.minVisibleIndex -= BoardStripConfig.boardsToScroll;
					if (ctrl.minVisibleIndex < 0) {
						ctrl.minVisibleIndex = 0;
					}

					ctrl.maxVisibleIndex = ctrl.minVisibleIndex + BoardStripConfig.maxVisibleBoards - 1;
					ctrl.rectifyMaxVisibleBoards();

					$timeout.cancel(oldTimeout);
					angular.element(element[0].querySelector(".boardstrip-container")).css({
						"left": calculateAndGetBoardstripContainerAdjustment() + "px"
					});

					$timeout(function () {
						var currentBoardArray = element[0].querySelectorAll('[b2b-board]');

						/* Remove tabindex from non-visible boards */
						updateBoardsTabIndex(currentBoardArray, ctrl.minVisibleIndex, ctrl.maxVisibleIndex);

						if (ctrl.minVisibleIndex === 0) {
							try {
								element[0].querySelector('div.boardstrip-item--add').focus();
							} catch (e) { /* IE8 may throw exception */ }
						}
					});
				};

				scope.isPrevBoard = function () {
					return (ctrl.minVisibleIndex > 0);
				};
				scope.isNextBoard = function () {
					return (ctrl.getBoardsMasterArrayLength() - 1 > ctrl.maxVisibleIndex);
				};

				ngModelCtrl.$render = function () {
					if (ngModelCtrl.$viewValue || ngModelCtrl.$viewValue === 0) {
						var newCurrentIndex = ngModelCtrl.$viewValue;

						if (!(newCurrentIndex = parseInt(newCurrentIndex, 10))) {
							newCurrentIndex = 0;
						}

						if (newCurrentIndex <= 0) {
							ctrl.resetBoardStrip();
							newCurrentIndex = 0;

							var currentBoardArray = element[0].querySelectorAll('[b2b-board]');
							if (currentBoardArray.length !== 0) {
								animateBoardstripContainerAdjustment(currentBoardArray[0]);
							} else {
								element[0].querySelector('div.boardstrip-item--add').focus();
							}
							/* Update tabindecies to ensure keyboard navigation behaves correctly */
							updateBoardsTabIndex(currentBoardArray, ctrl.minVisibleIndex, ctrl.maxVisibleIndex);
						} else if (newCurrentIndex >= ctrl.getBoardsMasterArrayLength()) {
							ctrl.maxVisibleIndex = ctrl.getBoardsMasterArrayLength() - 1;
							ctrl.rectifyMaxVisibleBoards();
							ctrl.minVisibleIndex = Math.max(ctrl.maxVisibleIndex - BoardStripConfig.maxVisibleBoards + 1, 0);

							newCurrentIndex = ctrl.maxVisibleIndex;

							$timeout(function () {
								var currentBoardArray = element[0].querySelectorAll('[b2b-board]');
								animateBoardstripContainerAdjustment(currentBoardArray[newCurrentIndex]);
								/* Update tabindecies to ensure keyboard navigation behaves correctly */
								updateBoardsTabIndex(currentBoardArray, ctrl.minVisibleIndex, ctrl.maxVisibleIndex);
							});
						} else {

							if (!(newCurrentIndex >= ctrl.minVisibleIndex && newCurrentIndex <= ctrl.maxVisibleIndex)) {
								ctrl.minVisibleIndex = newCurrentIndex;
								ctrl.maxVisibleIndex = ctrl.minVisibleIndex + BoardStripConfig.maxVisibleBoards - 1;
								ctrl.rectifyMaxVisibleBoards();

								if (ctrl.getBoardsMasterArrayLength() < BoardStripConfig.maxVisibleBoards) {
									ctrl.minVisibleIndex = 0;
								} else {
									ctrl.minVisibleIndex = Math.max(ctrl.maxVisibleIndex - BoardStripConfig.maxVisibleBoards + 1, 0);
								}

								$timeout(function () {
									var currentBoardArray = element[0].querySelectorAll('[b2b-board]');
									animateBoardstripContainerAdjustment(currentBoardArray[newCurrentIndex]);
									/* Update tabindecies to ensure keyboard navigation behaves correctly */
									updateBoardsTabIndex(currentBoardArray, ctrl.minVisibleIndex, ctrl.maxVisibleIndex);
								});
							}
						}
						scope.currentIndex = newCurrentIndex;
						ngModelCtrl.$setViewValue(newCurrentIndex);
					} else {
						ctrl.resetBoardStrip();
						ngModelCtrl.$setViewValue(0);
					}
				};

				scope.$watch('currentIndex', function (newVal, oldVal) {
					if (newVal !== oldVal && ngModelCtrl && ngModelCtrl.$viewValue !== newVal) {
						ngModelCtrl.$setViewValue(newVal);
					}
				});
			}
		};
	}])
	.directive('b2bAddBoard', ['BoardStripConfig', '$parse', function (BoardStripConfig, $parse) {
	    return {
	        restrict: 'AE',
	        replace: true,
	        require: '^b2bBoardStrip',
	        scope: {
	            onAddBoard: '&?'
	        },
	        templateUrl: 'b2bTemplate/boardstrip/b2bAddBoard.html',
	        link: function (scope, element, attrs, ctrl) {
	            scope.addBoard = function () {
	                if (attrs['onAddBoard']) {
	                    scope.onAddBoard = $parse(scope.onAddBoard);
	                    scope.onAddBoard();
	                    ctrl.setAddBoardPressedFlag(true);
	                }
	            };
	        }
	    };
	}])
	.directive('b2bBoardNavigation', ['keymap', 'events', function (keymap, events) {
		return {
			restrict: 'AE',
			link: function (scope, elem) {

				var prevElem = keymap.KEY.LEFT;
				var nextElem = keymap.KEY.RIGHT;

				elem.bind('keydown', function (ev) {

					if (!(ev.keyCode)) {
						ev.keyCode = ev.which;
					}

					switch (ev.keyCode) {
					case nextElem:
						events.preventDefault(ev);
						events.stopPropagation(ev);

						if (elem[0].nextElementSibling && parseInt(angular.element(elem[0].nextElementSibling).attr('tabindex')) >= 0) {
							angular.element(elem[0])[0].nextElementSibling.focus();
						} else {
							/* IE8 fix */
							var el = angular.element(elem[0])[0];
							do {
								if (el.nextSibling) {
									el = el.nextSibling;
								} else {
									break;
								}
							} while (el && el.tagName !== 'LI');

							if (el.tagName && el.tagName === 'LI' && parseInt(angular.element(el).attr('tabindex')) >= 0) {
								el.focus();
							}
						}

						break;
					case prevElem:
						events.preventDefault(ev);
						events.stopPropagation(ev);

						if (elem[0].previousElementSibling && parseInt(angular.element(elem[0].previousElementSibling).attr('tabindex')) >= 0) {
							angular.element(elem[0])[0].previousElementSibling.focus();
						} else {
							/* IE8 fix */
							var el1 = angular.element(elem[0])[0];
							do {
								if (el1.previousSibling) {
									el1 = el1.previousSibling;
								} else {
									break;
								}
							} while (el1 && el1.tagName !== 'LI');

							if (el1.tagName && el1.tagName === 'LI' && parseInt(angular.element(el1).attr('tabindex')) >= 0) {
								el1.focus();
							}
						}
						break;
					default:
						break;
					}
				});
			}
		};
	}]);
/** 
 * @ngdoc directive 
 * @name Template.att:Bootstrap Grid Template
 * 
 * @description 
 *  <file src="src/bootstrapGridTemplate/docs/readme.md" /> 
 * 
 * @example 
 *  <section id="code">
        <example module="b2b.att"> 
            <file src="src/bootstrapGridTemplate/docs/demo.html" /> 
            <file src="src/bootstrapGridTemplate/docs/demo.js" /> 
       </example> 
    </section>    
 * 
 */
angular.module('b2b.att.bootstrapGridTemplate', [])
  
/**
 * @ngdoc directive
 * @name Navigation.att:breadcrumbs
 *
 * @description
 *  <file src="src/breadcrumbs/docs/readme.md" />
 * @usage
    <ul class="breadcrumb">
        <li ng-repeat="link in breadCrumbsLink"><a tabindex="{{(idx==$index)?-1:0}}" href='javascript:void(0)' ng-click="clickActive($index)" ng-class="{'active':idx==$index, '': idx!=$index}">{{link.title}}</a></li>
    </ul>
 * @example
 <example module="b2b.att">
 <file src="src/breadcrumbs/docs/demo.html" />
 <file src="src/breadcrumbs/docs/demo.js" />
 </example>
 */
angular.module('b2b.att.breadcrumbs',[])
/**
 * @ngdoc directive
 * @name Buttons, links & UI controls.att:buttonGroups
 *
 * @description
 *  <file src="src/buttonGroups/docs/readme.md" />
 *
 * @usage
<h2>Radio Aproach</h2>
<div class="btn-group" b2b-key prev="37,38" next="39,40" circular-traversal role="radiogroup">
    <button type="button" class="btn btn-secondary" b2b-key-item ng-focus="radioModel='Button 1'" ng-model="radioModel" b2b-btn-radio="'Button 1'" tabindex="{{(!radioModel || 'Button 1'===radioModel)?0:-1}}">Button 1</button>
    <button type="button" class="btn btn-secondary" b2b-key-item ng-focus="radioModel='Button 2'" ng-model="radioModel" b2b-btn-radio="'Button 2'" tabindex="{{(!radioModel || 'Button 2'===radioModel)?0:-1}}">Button 2</button>
    <button type="button" class="btn btn-secondary" b2b-key-item ng-focus="radioModel='Button 3'" ng-model="radioModel" b2b-btn-radio="'Button 3'" tabindex="{{(!radioModel || 'Button 3'===radioModel)?0:-1}}">Button 3</button>
</div>

<h2>Checkbox Aproach</h2>
<span b2b-button-group class="btn-group btn-fullwidth" role="group" max-select="3" ng-model="checkModel1">
    <button type="button" class="btn btn-secondary" ng-model="checkModel1.Button1" b2b-btn-checkbox>Button1</button>
    <button type="button" class="btn btn-secondary" ng-model="checkModel1.Button2" b2b-btn-checkbox>Button2</button>
    <button type="button" class="btn btn-secondary" ng-model="checkModel1.Button3" b2b-btn-checkbox>Button3</button>
    <button type="button" class="btn btn-secondary" ng-model="checkModel1.Button4" b2b-btn-checkbox>Button4</button>
    <button type="button" class="btn btn-secondary" ng-model="checkModel1.Button5" b2b-btn-checkbox>Button5</button>
</span>
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/buttonGroups/docs/demo.html" />
            <file src="src/buttonGroups/docs/demo.js" />
       </example>
        </section>
 *
 */
angular.module('b2b.att.buttonGroups', ['b2b.att.utilities'])
    .constant('buttonConfig', {
        activeClass: 'active',
        toggleEvent: 'click'
    })
    .directive('b2bBtnRadio', ['buttonConfig', function (buttonConfig) {
        var activeClass = buttonConfig.activeClass || 'active';
        var toggleEvent = buttonConfig.toggleEvent || 'click';

        return {
            require: 'ngModel',
            link: function (scope, element, attrs, ngModelCtrl) {
                var notMobile = !/Android|webOS|iPhone|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);

                if (notMobile) {
                    element.bind('focus', function () {
                        scope.$apply(function () {
                            ngModelCtrl.$setViewValue(scope.$eval(attrs.b2bBtnRadio));
                            ngModelCtrl.$render();
                        });
                    });
                }

                element.attr('role', 'radio');

                //model -> UI
                ngModelCtrl.$render = function () {
                    element.toggleClass(activeClass, angular.equals(ngModelCtrl.$modelValue, scope.$eval(attrs.b2bBtnRadio)));
                    if (angular.equals(ngModelCtrl.$modelValue, scope.$eval(attrs.b2bBtnRadio))) {
                        element.attr("aria-checked", true);
                    } else {
                        element.attr("aria-checked", false);
                    }
                };

                //ui->model
                element.bind(toggleEvent, function () {
                    if (!element.hasClass(activeClass)) {
                        scope.$apply(function () {
                            ngModelCtrl.$setViewValue(scope.$eval(attrs.b2bBtnRadio));
                            ngModelCtrl.$render();
                        });
                    }
                });
            }
        };
    }])
    .directive('b2bBtnCheckbox', ['buttonConfig', function (buttonConfig) {
        var activeClass = buttonConfig.activeClass || 'active';
        var toggleEvent = buttonConfig.toggleEvent || 'click';

        return {
            require: ['ngModel', '^^b2bButtonGroup'],
            link: function (scope, element, attrs, ctrls) {

                var ngModelCtrl = ctrls[0];
                var parentCtrl = ctrls[1];

                element.attr('role', 'checkbox');
                element.attr('aria-describedby', parentCtrl.getStateDescriptionElemId());

                function getTrueValue() {
                    var trueValue = scope.$eval(attrs.b2bBtnCheckboxTrue);
                    return angular.isDefined(trueValue) ? trueValue : true;
                }

                function getFalseValue() {
                    var falseValue = scope.$eval(attrs.b2bBtnCheckboxFalse);
                    return angular.isDefined(falseValue) ? falseValue : false;
                }

                //model -> UI
                ngModelCtrl.$render = function () {
                    element.toggleClass(activeClass, angular.equals(ngModelCtrl.$modelValue, getTrueValue()));
                    if ((angular.equals(ngModelCtrl.$modelValue, getTrueValue()))) {
                        element.attr("aria-checked", true);
                    } else {
                        element.attr("aria-checked", false);
                    }
                };

                //ui->model
                element.bind(toggleEvent, function () {
                    scope.$apply(function () {
                        ngModelCtrl.$setViewValue(element.hasClass(activeClass) ? getFalseValue() : getTrueValue());
                        ngModelCtrl.$render();
                    });
                });
            }
        };
    }])
    .directive('b2bButtonGroup', ['$timeout', '$compile', function ($timeout, $compile) {
        return {
            restrict: 'A',
            scope: {
                maxSelect: "=",
                ngModelButtonState: '=ngModel'
            },
            controller: ['$scope', '$element', function ($scope, $element) {
                $scope.nSel = 0;

                var stateDescriptionElem = angular.element('<span id="b2b_button_group_' + $scope.$id + '" class="hide" aria-hidden="true">{{nSel}} of {{maxSelect}} options selected.</span>');
                $compile(stateDescriptionElem)($scope);
                $element.after(stateDescriptionElem);

                this.getStateDescriptionElemId = function () {
                    return stateDescriptionElem.attr('id');
                };
            }],
            link: function (scope, element) {


                var executeFxn = function () {
                    scope.nSel = 0;
                    angular.forEach(scope.ngModelButtonState, function (value, key) {
                        if (value === true) {
                            scope.nSel += 1;
                        }
                    });

                    if (scope.nSel >= scope.maxSelect) {
                        angular.forEach(element.children(), function (chd) {
                            if (chd.className.indexOf('active') < 0) {
                                chd.disabled = true;
                                chd.setAttribute('aria-disabled', true);
                            }
                        });
                    } else {
                        angular.forEach(element.children(), function (chd) {
                            chd.disabled = false;
                            chd.setAttribute('aria-disabled', false);
                        });
                    }
                    scope.$digest();
                };

                $timeout(function () {
                    executeFxn();
                });
                element.bind('click', executeFxn);
            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Buttons, links & UI controls.att:buttons
 * @element input
 * @function
 *
 * @description
 *  <file src="src/buttons/docs/readme.md" />
 * @usage
 *
Button shape
<button class="btn" type="button">Button</button> button.btn (button shape only)
<button aria-label="Custom aria label" class="btn" type="button">Button</button> button.btn (button shape only) with custom aria label
<button aria-label="Click on button/Press enter" class="btn" type="button" onclick="javascript:alert('It works!');">Click on button/Press enter</button> button.btn with click functionality
<a b2b-keyup-click="32" href="javascript:void(0)" class="btn" role="button">Button</a> a.btn (button shape only)
<button class="btn btn-primary">Button</button> .btn-primary
<a b2b-keyup-click="32" href="javascript:void(0)" class="btn btn-primary" role="button">Button</a> a.btn-primary

5 Button colors
<button class="btn btn-secondary">Button</button> .btn-secondary
<a b2b-keyup-click="32" href="javascript:void(0)" class="btn btn-secondary" role="button">Button</a> a.btn-secondary
<button class="btn btn-alt">Button</button> .btn-alt
<a b2b-keyup-click="32" href="javascript:void(0)" class="btn btn-alt" role="button">Button</a> a.btn-alt
<button class="btn btn-specialty">Button</button> .btn-specialty
<a b2b-keyup-click="32" href="javascript:void(0)" class="btn btn-specialty" role="button">Button</a> a.btn-specialty
<button class="btn btn-specialty" disabled="">Button</button> disabled="disabled"
<a b2b-keyup-click="32" aria-disabled="true" href="javascript:void(0)" class="btn btn-primary disabled" role="button">Button</a> a.disabled

3 button heights
<button class="btn btn-secondary">Button</button> .btn is default and 46px height
<button class="btn btn-secondary btn-medium">Button</button> .btn-medium is 42px
<button class="btn btn-secondary btn-small">Button</button> .btn-small is 36px

.row-nowrap 2 up buttons
<div class="row-nowrap">
    <button class="btn btn-secondary btn-fullwidth" type="button">Cancel</button>
    <button class="btn btn-primary btn-fullwidth" type="button">Continue</button>
</div>

.row 2 up buttons (desktop) stacked (mobile) (different order)
<div class="row cta-button-group">
    <button class="span btn btn-secondary btn-fullwidth hidden-phone" type="button">Cancel</button>
    <button class="span btn btn-primary btn-fullwidth" type="button">Continue</button>
    <button class="span btn btn-secondary btn-fullwidth visible-phone" type="button">Cancel</button>
</div>

 * @example
 *  <section id="code">
               <b>HTML + AngularJS</b>
 *             	<example module="b2b.att">
 *  	        <file src="src/buttons/docs/demo.html" />
                 <file src="src/buttons/docs/demo.js" />
 *              </example>
            </section>
 *
 */
angular.module('b2b.att.buttons', ['b2b.att.utilities']);
/**
 * @ngdoc directive
 * @name Forms.att:calendar
 *
 * @description
 *  <file src="src/calendar/docs/readme.md" />
 * @usage
 *  <input type="text" ng-model="dt" b2b-datepicker>
 *
 * @example
   <section id="code">
    <b>HTML + AngularJS</b>
    <example module="b2b.att">
     <file src="src/calendar/docs/demo.html" />
     <file src="src/calendar/docs/demo.js" />
    </example>
   </section>
 */
angular.module('b2b.att.calendar', ['b2b.att.position', 'b2b.att.utilities'])

.constant('b2bDatepickerConfig', {
    dateFormat: 'MM/dd/yyyy',
    dayFormat: 'd',
    monthFormat: 'MMMM',
    yearFormat: 'yyyy',
    dayHeaderFormat: 'EEEE',
    dayTitleFormat: 'MMMM yyyy',
    disableWeekend: false,
    disableSunday: false,
    disableDates: null,
    onSelectClose: null,
    startingDay: 0,
    minDate: null,
    maxDate: null,
    dueDate: null,
    fromDate: null,
    legendIcon: null,
    legendMessage: null,
    calendarDisabled: false,
    collapseWait: 0,
    orientation: 'right',
    inline: false,
    helperText: 'The date you selected is $date. In case of mobile double tap to open calendar. Select a date to close the calendar.',
    datepickerEvalAttributes: ['dateFormat', 'dayFormat', 'monthFormat', 'yearFormat', 'dayHeaderFormat', 'dayTitleFormat', 'disableWeekend', 'disableSunday', 'startingDay', 'collapseWait', 'orientation'],
    datepickerWatchAttributes: ['min', 'max', 'due', 'from', 'legendIcon', 'legendMessage', 'ngDisabled'],
    datepickerFunctionAttributes: ['disableDates', 'onSelectClose']
})

.factory('b2bDatepickerService', ['b2bDatepickerConfig', 'dateFilter', function (b2bDatepickerConfig, dateFilter) {
    var setAttributes = function (attr, elem) {
        if (angular.isDefined(attr) && attr !== null && angular.isDefined(elem) && elem !== null) {
            var attributes = b2bDatepickerConfig.datepickerEvalAttributes.concat(b2bDatepickerConfig.datepickerWatchAttributes, b2bDatepickerConfig.datepickerFunctionAttributes);
            for (var key in attr) {
                var val = attr[key];
                if (attributes.indexOf(key) !== -1 && angular.isDefined(val)) {
                    elem.attr(key.toSnakeCase(), key);
                }
            }
        }
    };

    var bindScope = function (attr, scope) {
        if (angular.isDefined(attr) && attr !== null && angular.isDefined(scope) && scope !== null) {
            var evalFunction = function (key, val) {
                scope[key] = scope.$parent.$eval(val);
            };

            var watchFunction = function (key, val) {
                scope.$parent.$watch(val, function (value) {
                    scope[key] = value;
                });
                scope.$watch(key, function (value) {
                    scope.$parent[val] = value;
                });
            };

            var evalAttributes = b2bDatepickerConfig.datepickerEvalAttributes;
            var watchAttributes = b2bDatepickerConfig.datepickerWatchAttributes;
            for (var key in attr) {
                var val = attr[key];
                if (evalAttributes.indexOf(key) !== -1 && angular.isDefined(val)) {
                    evalFunction(key, val);
                } else if (watchAttributes.indexOf(key) !== -1 && angular.isDefined(val)) {
                    watchFunction(key, val);
                }
            }
        }
    };

    return {
        setAttributes: setAttributes,
        bindScope: bindScope
    };
}])

.controller('b2bDatepickerController', ['$scope', '$attrs', 'dateFilter', '$element', '$position', 'b2bDatepickerConfig', function ($scope, $attrs, dateFilter, $element, $position, dtConfig) {
    var format = {
            date: getValue($attrs.dateFormat, dtConfig.dateFormat),
            day: getValue($attrs.dayFormat, dtConfig.dayFormat),
            month: getValue($attrs.monthFormat, dtConfig.monthFormat),
            year: getValue($attrs.yearFormat, dtConfig.yearFormat),
            dayHeader: getValue($attrs.dayHeaderFormat, dtConfig.dayHeaderFormat),
            dayTitle: getValue($attrs.dayTitleFormat, dtConfig.dayTitleFormat),
            disableWeekend: getValue($attrs.disableWeekend, dtConfig.disableWeekend),
            disableSunday: getValue($attrs.disableSunday, dtConfig.disableSunday)
        },
        startingDay = getValue($attrs.startingDay, dtConfig.startingDay);

    if($attrs.disableDates !== undefined) {
        format.disableDates = $attrs.disableDates;
    } else {
       format.disableDates =  dtConfig.disableDates;     
    }
    $scope.minDate = dtConfig.minDate ? $scope.resetTime(dtConfig.minDate) : null;
    $scope.maxDate = dtConfig.maxDate ? $scope.resetTime(dtConfig.maxDate) : null;
    $scope.dueDate = dtConfig.dueDate ? $scope.resetTime(dtConfig.dueDate) : null;
    $scope.fromDate = dtConfig.fromDate ? $scope.resetTime(dtConfig.fromDate) : null;
    $scope.legendIcon = dtConfig.legendIcon ? dtConfig.legendIcon : null;
    $scope.legendMessage = dtConfig.legendMessage ? dtConfig.legendMessage : null;
    $scope.ngDisabled = dtConfig.calendarDisabled ? dtConfig.calendarDisabled : null;
    $scope.collapseWait = getValue($attrs.collapseWait, dtConfig.collapseWait);
    $scope.orientation = getValue($attrs.orientation, dtConfig.orientation);
    $scope.onSelectClose = getValue($attrs.onSelectClose, dtConfig.onSelectClose);

    $scope.inline = $attrs.inline === 'true' ? true : dtConfig.inline;

    function getValue(value, defaultValue) {
        return angular.isDefined(value) ? $scope.$parent.$eval(value) : defaultValue;
    }

    function getDaysInMonth(year, month) {
        return new Date(year, month, 0).getDate();
    }

    function getDates(startDate, n) {
        var dates = new Array(n);
        var current = startDate,
            i = 0;
        while (i < n) {
            dates[i++] = new Date(current);
            current.setDate(current.getDate() + 1);
        }
        return dates;
    }

    this.updatePosition = function (b2bDatepickerPopupTemplate) {
        $scope.position = $position.offset($element);
        $scope.position.top = $scope.position.top + $element.prop('offsetHeight');
        $scope.position.left = $scope.position.left - (((b2bDatepickerPopupTemplate && b2bDatepickerPopupTemplate.prop('offsetWidth')) || 290) - $element.prop('offsetWidth'));
    };

    this.isDateInRange = function(date) {

        if ((compare(date, $scope.minDate) >= 0) && (compare(date, $scope.maxDate) <= 0)) {
            return true;
        } else  {
            return false;
        }
        return false;
    }

    this.isDisbaledDate = function(date) {
        if ($attrs.from && !angular.isDate($scope.fromDate)) {
            return true;
        }
        if (format.disableWeekend === true && (dateFilter(date, format.dayHeader) === "Saturday" || dateFilter(date, format.dayHeader) === "Sunday")) {
            return true;
        }
        if (format.disableSunday === true && (dateFilter(date, format.dayHeader) === "Sunday")) {
            return true;
        }
    
        return (($scope.minDate && compare(date, $scope.minDate) < 0) || ($scope.maxDate && compare(date, $scope.maxDate) > 0) || ($scope.datesCallBack({
            date: date
        })));

    }
    function isSelected(dt) {
        if (dt && angular.isDate($scope.currentDate) && compare(dt, $scope.currentDate) === 0) {
            return true;
        }
        return false;
    }

    function isFromDate(dt) {
        if (dt && angular.isDate($scope.fromDate) && compare(dt, $scope.fromDate) === 0) {
            return true;
        }
        return false;
    }

    function isDateRange(dt) {
        if (dt && $scope.fromDate && angular.isDate($scope.currentDate) && (compare(dt, $scope.fromDate) >= 0) && (compare(dt, $scope.currentDate) <= 0)) {
            return true;
        } else if (dt && $scope.fromDate && compare(dt, $scope.fromDate) === 0) {
            return true;
        }
        return false;
    }

    function isOld(date, currentMonthDate) {
        if (date && currentMonthDate && (new Date(date.getFullYear(), date.getMonth(), 1, 0, 0, 0).getTime() < new Date(currentMonthDate.getFullYear(), currentMonthDate.getMonth(), 1, 0, 0, 0).getTime())) {
            return true;
        } else {
            return false;
        }
    }

    function isNew(date, currentMonthDate) {
        if (date && currentMonthDate && (new Date(date.getFullYear(), date.getMonth(), 1, 0, 0, 0).getTime() > new Date(currentMonthDate.getFullYear(), currentMonthDate.getMonth(), 1, 0, 0, 0).getTime())) {
            return true;
        } else {
            return false;
        }
    }

    function isPastDue(dt) {
        if ($scope.dueDate) {
            return (dt > $scope.dueDate);
        }
        return false;
    }

    function isDueDate(dt) {
        if ($scope.dueDate) {
            return (dt.getTime() === $scope.dueDate.getTime());
        }
        return false;
    }

    var isDisabled = function (date, currentMonthDate) {
        if ($attrs.from && !angular.isDate($scope.fromDate)) {
            return true;
        }
        if (format.disableWeekend === true && (dateFilter(date, format.dayHeader) === "Saturday" || dateFilter(date, format.dayHeader) === "Sunday")) {
            return true;
        }
        if (format.disableSunday === true && (dateFilter(date, format.dayHeader) === "Sunday")) {
            return true;
        }
        if (isOld(date, currentMonthDate) || isNew(date, currentMonthDate)) {
            return true;
        }
        return (($scope.minDate && compare(date, $scope.minDate) < 0) || ($scope.maxDate && compare(date, $scope.maxDate) > 0) || ($scope.datesCallBack({
            date: date
        })));
    };

    var compare = function (date1, date2) {
        if(date2 !== null ){
            return (new Date(date1.getFullYear(), date1.getMonth(), date1.getDate()) - new Date(date2.getFullYear(), date2.getMonth(), date2.getDate()));    
        } else {
            return false
        }
        
    };

    function isMinDateAvailable(startDate, endDate) {
        if (($scope.minDate && $scope.minDate.getTime() >= startDate.getTime()) && ($scope.minDate.getTime() <= endDate.getTime())) {
            $scope.disablePrev = true;
            $scope.visibilityPrev = "hidden";
        } else {
            $scope.disablePrev = false;
            $scope.visibilityPrev = "visible";
        }
    }

    function isMaxDateAvailable(startDate, endDate) {
        if (($scope.maxDate && $scope.maxDate.getTime() >= startDate.getTime()) && ($scope.maxDate.getTime() <= endDate.getTime())) {
            $scope.disableNext = true;
            $scope.visibilityNext = "hidden";
        } else {
            $scope.disableNext = false;
            $scope.visibilityNext = "visible";
        }
    }

    function getLabel(label) {
        if (label) {
            var labelObj = {
                pre: label.substr(0, 1).toUpperCase(),
                post: label
            };
            return labelObj;
        }
        return;
    }

    function makeDate(date, dayFormat, dayHeaderFormat, isSelected, isFromDate, isDateRange, isOld, isNew, isDisabled, dueDate, pastDue) {
        return {
            date: date,
            label: dateFilter(date, dayFormat),
            header: dateFilter(date, dayHeaderFormat),
            selected: !!isSelected,
            fromDate: !!isFromDate,
            dateRange: !!isDateRange,
            oldMonth: !!isOld,
            nextMonth: !!isNew,
            disabled: !!isDisabled,
            dueDate: !!dueDate,
            pastDue: !!pastDue,
            focusable: !((isDisabled && !(isSelected || isDateRange)) || (isOld || isNew))
        };
    }

    this.modes = [
        {
            name: 'day',
            getVisibleDates: function (date) {
                var year = date.getFullYear(),
                    month = date.getMonth(),
                    firstDayOfMonth = new Date(year, month, 1),
                    lastDayOfMonth = new Date(year, month + 1, 0);
                var difference = startingDay - firstDayOfMonth.getDay(),
                    numDisplayedFromPreviousMonth = (difference > 0) ? 7 - difference : -difference,
                    firstDate = new Date(firstDayOfMonth),
                    numDates = 0;

                if (numDisplayedFromPreviousMonth > 0) {
                    firstDate.setDate(-numDisplayedFromPreviousMonth + 1);
                    numDates += numDisplayedFromPreviousMonth; // Previous
                }
                numDates += getDaysInMonth(year, month + 1); // Current
                numDates += (7 - numDates % 7) % 7; // Next

                var days = getDates(firstDate, numDates),
                    labels = new Array(7);
                for (var i = 0; i < numDates; i++) {
                    var dt = new Date(days[i]);
                    days[i] = makeDate(dt,
                        format.day,
                        format.dayHeader,
                        isSelected(dt),
                        isFromDate(dt),
                        isDateRange(dt),
                        isOld(dt, date),
                        isNew(dt, date),
                        isDisabled(dt, date),
                        isDueDate(dt),
                        isPastDue(dt));
                }
                for (var j = 0; j < 7; j++) {
                    labels[j] = getLabel(dateFilter(days[j].date, format.dayHeader));
                }
                isMinDateAvailable(firstDayOfMonth, lastDayOfMonth);
                isMaxDateAvailable(firstDayOfMonth, lastDayOfMonth);
                return {
                    objects: days,
                    title: dateFilter(date, format.dayTitle),
                    labels: labels
                };
            },
            split: 7,
            step: {
                months: 1
            }
        }
    ];
}])

.directive('b2bDatepicker', ['$parse', '$log', '$timeout', '$document', '$documentBind', '$isElement', '$templateCache', '$compile', 'trapFocusInElement', '$position', '$window', '$filter', 'b2bDatepickerConfig', function ($parse, $log, $timeout, $document, $documentBind, $isElement, $templateCache, $compile, trapFocusInElement, $position, $window, $filter, b2bDatepickerConfig) {
    return {
        restrict: 'EA',
        scope: { 
            model: '=ngModel',
            datesCallBack: '&disableDates',
            onSelectClose: '&',
            disabledInput: '=?ngDisabled',
            newSelectedDate: '=selectNextMonth'
        },
        require: ['b2bDatepicker', 'ngModel', '?^b2bDatepickerGroup'],
        controller: 'b2bDatepickerController',
        link: function (scope, element, attrs, ctrls) {
            var datepickerCtrl = ctrls[0],
                ngModel = ctrls[1],
                b2bDatepickerGroupCtrl = ctrls[2];
            var b2bDatepickerPopupTemplate;
            var isCalendarOpened = false;

            // Configuration parameters
            var mode = 0,
                selected;
            scope.isOpen = false;
            var isValidDate = false;

            scope.headers = [];
            scope.footers = [];

            if(scope.disabledInput === undefined || scope.disabledInput === '') {
                scope.disabledInput = false;
            } 
            if(attrs.inline == 'true'){     
                element.after($compile($templateCache.get('b2bTemplate/calendar/datepicker-popup.html'))(scope));
                var temp = element.after();
                element.remove();
                element = temp; 
            } else {
                var buttonTabIndex =  scope.disabledInput===true ? -1 : 0; 
                element.after($compile('<button type="button" class="btn-calendar-icon" ng-disabled="disabledInput" aria-haspopup="true" aria-label="calendar popup"><i class="icon-calendar b2b-calendar-icon" aria-hidden="true" ng-class=\"{\'disabled\': disabledInput}\" ></i></button>')(scope));
                element.attr('placeholder', 'MM/dd/yyyy');
                element.attr('b2b-format-date', b2bDatepickerConfig.dateFormat); 
            }

            scope.$watch('newSelectedDate', function(newVal,oldValue){

                if(newVal !== oldValue){
                    if (newVal !== '' && newVal !== undefined){
                        selectNextAvailableMonth(newVal);
                    }
                }
            });

            scope.$watch('model', function(val) { 
 
                if(val !== undefined && val !== '') { 
                    var date_regex = /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/ ; 

                    if(scope.model.length <8){ 
                        ngModel.$setValidity('datePattern', false); 
                    } else { 
                        var inputDate = Date.parse(scope.model);
                        var inputElement
                        if(isNaN(inputDate)) {
                            ngModel.$setValidity('datePattern', false); 
                        } else {
                            inputElement = $filter('date')(scope.model, "MM/dd/yyyy");
                        }
                        if(!date_regex.test(inputElement) ){ 
                            ngModel.$setValidity('datePattern', false); 
                        } else {
                                ngModel.$setValidity('datePattern', true); 
                                element[0].value = $filter('date')(scope.model, "MM/dd/yyyy");
                                var parts = element[0].value.split('/');
                                var enteredDate = new Date(parts[2], parts[0] - 1, parts[1]);
                                if (datepickerCtrl.isDateInRange(enteredDate)) {
                                        ngModel.$setValidity('outOfRange', true);
                                        if (!datepickerCtrl.isDisbaledDate(enteredDate)) {
                                            ngModel.$setValidity('disabledDate', true);
                                        } else {
                                            ngModel.$setValidity('disabledDate', false);
                                        }
                                } else {
                                        ngModel.$setValidity('outOfRange', false);
                                    }
                                }
                        }
                    } else if (scope.newSelectedDate !== '' && scope.newSelectedDate !== undefined){

                        selectNextAvailableMonth(scope.newSelectedDate);

                    } else {
                        ngModel.$setValidity('datePattern', true);
                    }

                });

            if (!ngModel) {
                $log.error("ng-model is required.");
                return; // do nothing if no ng-model
            }

            if(scope.model !== undefined && scope.model !== '') {
                element[0].value = $filter('date')(scope.model, "MM/dd/yyyy");
            }

            if (b2bDatepickerGroupCtrl) {
                b2bDatepickerGroupCtrl.registerDatepickerScope(scope);
            }


            var selectNextAvailableMonth = function(dateSelected) {

                var date_regexTwo = /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/ ; 

                if(dateSelected.length <8){ 
                    ngModel.$setValidity('datePattern', false); 
                } else { 
                    var nextInputDate = Date.parse(dateSelected);
                    var nextInputElement
                    if(isNaN(nextInputDate)) {
                        ngModel.$setValidity('datePattern', false); 
                    } else {
                        nextInputElement = $filter('date')(dateSelected, "MM/dd/yyyy");
                    }
                    if(!date_regexTwo.test(nextInputElement) ){ 
                        ngModel.$setValidity('datePattern', false); 
                    } else {
                            ngModel.$setValidity('datePattern', true); 
                            //var nextAvailableMonthSelected =
                            //element[0].value = $filter('date')(nextAvailableMonth, "MM/dd/yyyy");
                            var nextParts = $filter('date')(dateSelected, "MM/dd/yyyy").split('/');
                            var nextEnteredDate = new Date(nextParts[2], nextParts[0] - 1, nextParts[1]);
                            if (datepickerCtrl.isDateInRange(nextEnteredDate)) {
                                    ngModel.$setValidity('outOfRange', true);
                                    if (!datepickerCtrl.isDisbaledDate(nextEnteredDate)) {
                                        ngModel.$setValidity('disabledDate', true);
                                        scope.select(nextEnteredDate, false);
                                    } else {
                                        ngModel.$setValidity('disabledDate', false);
                                    }
                            } else {
                                    ngModel.$setValidity('outOfRange', false);
                                }
                            }
                    }

            }

            var calendarButton = angular.element(element[0].nextElementSibling);

            calendarButton.bind('click',function(){
                openCalendarPopup = false;
                if (!scope.ngDisabled) {
                    scope.isOpen = !scope.isOpen;
                    toggleCalendar(scope.isOpen);
                    scope.$apply();
                    datepickerCtrl.updatePosition(b2bDatepickerPopupTemplate);
                    $timeout(function () { 
                       // angular.element(element[0].querySelector('.datepicker-input')).scrollTop=0;
                    },10);
                }
            })
            var openCalendarPopup = false;

            element.bind('blur', function() {
                if(scope.model !== undefined && scope.model !== '') {
                    var dateEntered = scope.model;

                      var date_regex = /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/ ;

                    if(date_regex.test(dateEntered)) {       
                        var parts = dateEntered.split('/');
                        var enteredDate = new Date(parts[2],parts[0]-1,parts[1]);                           

                        if(datepickerCtrl.isDateInRange(enteredDate)) {
                            isValidDate -= 1;
                            ngModel.$setValidity('outOfRange', true);
                            $timeout(function(){
                                ngModel.$setValidity('outOfRange', true);
                            },10);
                            isValidDate = true;
                            if(!datepickerCtrl.isDisbaledDate(enteredDate)) {  
                               $timeout(function(){
                                    ngModel.$setValidity('disabledDate', true);
                               },10);   
                                scope.select(enteredDate); 
                                openCalendarPopup = true;
                            } else {
                                $timeout(function(){
                                    ngModel.$setValidity('disabledDate', false);
                                },10);
                                isValidDate = false;
                                openCalendarPopup = false;
                            }
  
                        } else {
                            isValidDate += 1;
                            $timeout(function(){
                                ngModel.$setValidity('outOfRange', false);
                            },10);
                            isValidDate = false;
                            openCalendarPopup = false;
                        }

                    }
                }
            });   

            var toggleCalendar = function (flag) {
                if (!scope.inline) {
                    if (flag) {
                        b2bDatepickerPopupTemplate = angular.element($templateCache.get('b2bTemplate/calendar/datepicker-popup.html'));
                        b2bDatepickerPopupTemplate = $compile(b2bDatepickerPopupTemplate)(scope);
                        $document.find('body').append(b2bDatepickerPopupTemplate);
                        b2bDatepickerPopupTemplate.bind('keydown', keyPress);
                        $timeout(function () {
                            scope.getFocus = true;
                            trapFocusInElement(flag, b2bDatepickerPopupTemplate);
                            scope.$apply();
                            $timeout(function () {
                                scope.getFocus = false; 
                                scope.$apply();
                            }, 100);
                            handleTabEvent();
                        });
                        angular.element(document.querySelector('.b2b-calendar-icon')).attr('aria-expanded','true');
                    } else {
                        if(!openCalendarPopup) {
                            b2bDatepickerPopupTemplate.unbind('keydown', keyPress);
                            b2bDatepickerPopupTemplate.remove();
                        }
                        element[0].focus();
                        scope.getFocus = false;
                        angular.element(document.querySelector('.b2b-calendar-icon')).attr('aria-expanded','false');
                        trapFocusInElement(flag, b2bDatepickerPopupTemplate);
                    }
                }
            };

            var handleTabEvent = function(){
                b2bDatepickerPopupTemplate.find('td').on('keydown', function (e) {
                    if (e.keyCode == '9') {
                        if(e.shiftKey){
                            if(b2bDatepickerPopupTemplate.find('tr')[0].querySelector('th.next')){
                                b2bDatepickerPopupTemplate.find('tr')[0].querySelector('th.next').focus();
                            }else{
                                b2bDatepickerPopupTemplate.find('tr')[0].querySelector('th.datepicker-switch').focus()
                            }        
                        }else{
                            if(b2bDatepickerPopupTemplate.find('tr')[0].querySelector('th.prev')){
                                b2bDatepickerPopupTemplate.find('tr')[0].querySelector('th.prev').focus();
                            }else{
                                b2bDatepickerPopupTemplate.find('tr')[0].querySelector('th.datepicker-switch').focus()
                            }
                        }
                        
                        e.preventDefault();
                        e.stopPropagation();
                    }
                });
            }

            var outsideClick = function (e) {
                var isElement = $isElement(angular.element(e.target), element, $document);
                var isb2bDatepickerPopupTemplate = $isElement(angular.element(e.target), b2bDatepickerPopupTemplate, $document);
                if (!(isElement || isb2bDatepickerPopupTemplate)) {
                    scope.isOpen = false;
                    toggleCalendar(scope.isOpen);
                    scope.$apply();
                }
            };

            var keyPress = function (ev) {
                if (!ev.keyCode) {
                    if (ev.which) {
                        ev.keyCode = ev.which;
                    } else if (ev.charCode) {
                        ev.keyCode = ev.charCode;
                    }
                }
                if (ev.keyCode) {
                    if (ev.keyCode === 27) {
                        scope.isOpen = false;
                        toggleCalendar(scope.isOpen);
                        ev.preventDefault();
                        ev.stopPropagation();
                    } else if (ev.keyCode === 33) {
                        !scope.disablePrev && scope.move(-1);
                        $timeout(function () {
                            scope.getFocus = true;
                            scope.$apply();
                            $timeout(function () {
                                scope.getFocus = false;
                                scope.$apply();
                            }, 100);
                        });
                        ev.preventDefault();
                        ev.stopPropagation();
                    } else if (ev.keyCode === 34) {
                        !scope.disableNext && scope.move(1);
                        $timeout(function () {
                            scope.getFocus = true;
                            scope.$apply();
                            $timeout(function () {
                                scope.getFocus = false;
                                scope.$apply();
                            }, 100);
                        });
                        ev.preventDefault();
                        ev.stopPropagation();
                    }
                    scope.$apply();
                }
            };

            $documentBind.click('isOpen', outsideClick, scope);

            var modalContainer = angular.element(document.querySelector('.modalwrapper'));
            var modalBodyContainer = angular.element(document.querySelector('.b2b-modal-body'));
            if (modalContainer) {
                modalContainer.bind('scroll', function () {
                    if (b2bDatepickerPopupTemplate) {
                        datepickerCtrl.updatePosition(b2bDatepickerPopupTemplate);
                        scope.$apply();
                    }
                });
            }
            if (modalBodyContainer) {
                modalBodyContainer.bind('scroll', function () {
                    if (b2bDatepickerPopupTemplate) {
                        datepickerCtrl.updatePosition(b2bDatepickerPopupTemplate);
                        var datepickerTextfield = $position.offset(element);
                        var modalBodyPosition = $position.offset(modalBodyContainer);

                        if (((datepickerTextfield.top + datepickerTextfield.height) < modalBodyPosition.top || datepickerTextfield.top > (modalBodyPosition.top + modalBodyPosition.height)) && scope.isOpen) {
                            scope.isOpen = false;
                            toggleCalendar(scope.isOpen);
                        }
                        scope.$apply();
                    }
                });
            }
            var window = angular.element($window);
            window.bind('resize', function () {
                if (b2bDatepickerPopupTemplate) {
                    datepickerCtrl.updatePosition(b2bDatepickerPopupTemplate);
                    scope.$apply();
                }
            });

            scope.$on('$destroy', function () {
                if (scope.isOpen) {
                    scope.isOpen = false;
                    toggleCalendar(scope.isOpen);
                }
            });

            scope.resetTime = function (date) {
                if (typeof date === 'string') {
                    date = date + 'T12:00:00';
                }
                var dt;
                if (!isNaN(new Date(date))) {
                    dt = new Date(date);
                } else {
                    return null;
                }
                return new Date(dt.getFullYear(), dt.getMonth(), dt.getDate());
            };

            if (attrs.min) {
                scope.$parent.$watch($parse(attrs.min), function (value) {
                    scope.minDate = value ? scope.resetTime(value) : null;
                    refill();
                });
            }
            if (attrs.max) {
                scope.$parent.$watch($parse(attrs.max), function (value) {
                    scope.maxDate = value ? scope.resetTime(value) : null;
                    refill();
                });
            }
            if (attrs.due) {
                scope.$parent.$watch($parse(attrs.due), function (value) {
                    scope.dueDate = value ? scope.resetTime(value) : null;
                    refill();
                });
            }
            if (attrs.from) {
                scope.$parent.$watch($parse(attrs.from), function (value) {
                    scope.fromDate = value ? scope.resetTime(value) : null;
                    refill();
                });
            }

            if (attrs.legendIcon) {
                scope.$parent.$watch(attrs.legendIcon, function (value) {
                    scope.legendIcon = value ? value : null;
                    refill();
                });
            }
            if (attrs.legendMessage) {
                scope.$parent.$watch(attrs.legendMessage, function (value) {
                    scope.legendMessage = value ? value : null;
                    refill();
                });
            }
            if (attrs.ngDisabled) {
                scope.$parent.$watch(attrs.ngDisabled, function (value) {
                    scope.ngDisabled = value ? value : null;
                });
            }

            // Split array into smaller arrays
            function split(arr, size) {
                var arrays = [];
                while (arr.length > 0) {
                    arrays.push(arr.splice(0, size));
                }
                return arrays;
            }

            function refill(date) {
                if (angular.isDate(date) && !isNaN(date)) {
                    selected = new Date(date);
                } else {
                    if (!selected) {
                        selected = new Date();
                    }
                }

                if (selected) {
                    var currentMode = datepickerCtrl.modes[mode],
                        data = currentMode.getVisibleDates(selected);
                    scope.rows = split(data.objects, currentMode.split);
                    var flag = false;
                    var startFlag = false;
                    var firstSelected = false;
                    for (var i = 0; i < scope.rows.length; i++) {
                        for (var j = 0; j < scope.rows[i].length; j++) {

                            if (scope.rows[i][j].label === "1" && !firstSelected) {
                                firstSelected = true;
                                var firstDay = scope.rows[i][j];
                            }

                            if (scope.rows[i][j].selected === true) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            break;
                        }
                    }
                    if (!flag) {
                        firstDay.firstFocus = true;
                    }

                    scope.labels = data.labels || [];
                    scope.title = data.title;

                    datepickerCtrl.updatePosition(b2bDatepickerPopupTemplate);
                }
            }

            scope.select = function (date, ngModelPresent) {
                
                if(ngModelPresent === undefined){
                    ngModelPresent = true;
                }
                var dt = new Date(date.getFullYear(), date.getMonth(), date.getDate());
                if (!scope.onSelectClose || (scope.onSelectClose && scope.onSelectClose({
                        date: dt
                    }) !== false)) {
                    if(ngModelPresent) {
                        scope.currentDate = dt;
                        element[0].value = $filter('date')(dt, "MM/dd/yyyy");                        
                    } else {
                        console.log("dt:"+dt)
                        refill(dt);
                    }
                    ngModel.$setValidity('outOfRange', true);
                    if (angular.isNumber(scope.collapseWait)) {
                        $timeout(function () {
                            scope.isOpen = false;
                            toggleCalendar(scope.isOpen);
                        }, scope.collapseWait);
                    } else {
                        scope.isOpen = false;
                        toggleCalendar(scope.isOpen);
                    }
                }
            };

            scope.move = function (direction,$event) {
                var step = datepickerCtrl.modes[mode].step;
                selected.setDate(1);
                selected.setMonth(selected.getMonth() + direction * (step.months || 0));
                selected.setFullYear(selected.getFullYear() + direction * (step.years || 0));
                refill();

                $timeout(function () {
                    trapFocusInElement();
                    handleTabEvent();
                }, 100);

                $event.preventDefault();
                $event.stopPropagation();
            };

            scope.trapFocus = function () {
                $timeout(function () {
                    trapFocusInElement();
                }, 100);
            };

            scope.$watch('currentDate', function (value) {
                if (angular.isDefined(value) && value !== null) {
                    refill(value);
                } else {
                    refill();
                }
                ngModel.$setViewValue(value);
            });

            ngModel.$render = function () {
                scope.currentDate = ngModel.$viewValue;
            };

            var stringToDate = function (value) {
                if (!isNaN(new Date(value))) {
                    value = new Date(value);
                }
                return value;
            };
            ngModel.$formatters.unshift(stringToDate);
        }
    };
}])


.directive('b2bDatepickerGroup', [function () {
    return {
        restrict: 'EA',
        controller: ['$scope', '$element', '$attrs', function (scope, elem, attr) {
            this.$$headers = [];
            this.$$footers = [];
            this.registerDatepickerScope = function (datepickerScope) {
                datepickerScope.headers = this.$$headers;
                datepickerScope.footers = this.$$footers;
            };
        }],
        link: function (scope, elem, attr, ctrl) {}
    };
}])

.directive('b2bFormatDate', ['dateFilter', function (dateFilter) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, elem, attr, ctrl) {
            var b2bFormatDate = "";
            attr.$observe('b2bFormatDate', function (value) {
                b2bFormatDate = value;
            });
            var dateToString = function (value) {
                if (!isNaN(new Date(value))) {
                    return dateFilter(new Date(value), b2bFormatDate);
                }
                return value;
            };
            ctrl.$formatters.unshift(dateToString);
        }
    };
}])

.directive('b2bDatepickerHeader', [function () {
    return {
        restrict: 'EA',
        require: '^b2bDatepickerGroup',
        transclude: true,
        replace: true,
        template: '',
        compile: function (elem, attr, transclude) {
            return function link(scope, elem, attr, ctrl) {
                if (ctrl) {
                    ctrl.$$headers.push(transclude(scope, function () {}));
                }
                elem.remove();
            };
        }
    };
}])

.directive('b2bDatepickerFooter', [function () {
    return {
        restrict: 'EA',
        require: '^b2bDatepickerGroup',
        transclude: true,
        replace: true,
        template: '',
        compile: function (elem, attr, transclude) {
            return function link(scope, elem, attr, ctrl) {
                if (ctrl) {
                    ctrl.$$footers.push(transclude(scope, function () {}));
                }
                elem.remove();
            };
        }
    };
}]);
/**
 * @ngdoc directive
 * @name Template.att:cards 
 *
 * @description
 *  <file src="src/cards/docs/readme.md" />
 *
 * @usage

 *
 * @example
 *  <section id="code">   
 <example module="b2b.att">
 <file src="src/cards/docs/demo.html" />
 <file src="src/cards/docs/demo.js" />
 </example>
 </section>
 *
 */
angular.module('b2b.att.cards', ['ngMessages','b2b.att.utilities']);
/**
 * @ngdoc directive
 * @name Forms.att:checkboxes
 *
 * @description
 *  <file src="src/checkboxes/docs/readme.md" />
 * @usage
 * See demo section
 * @example
 <example module="b2b.att">
 <file src="src/checkboxes/docs/demo.html" />
 <file src="src/checkboxes/docs/demo.js" />
 </example>
 */
angular.module('b2b.att.checkboxes', ['b2b.att.utilities'])
.directive('b2bSelectGroup', [function (){
        return {
            restrict: 'A',
            require: 'ngModel',
            scope: {
                checkboxes: "="
            },
            link: function (scope, elem, attr, ctrl) {
                elem.bind('change', function () {
                    var isChecked = elem.prop('checked');
                    angular.forEach(scope.checkboxes, function (item) {
                        item.isSelected = isChecked;
                    });
                    scope.$apply();
                });
                scope.$watch('checkboxes', function () {
                    var setBoxes = 0;
                    if(scope.checkboxes === undefined) {
                        return;
                    }
                    angular.forEach(scope.checkboxes, function (item) {
                        if (item.isSelected) {
                            setBoxes++; 
                        } 
                    });
                    elem.prop('indeterminate', false);
                    if ( scope.checkboxes !==undefined && setBoxes === scope.checkboxes.length && scope.checkboxes.length > 0) { 
                        ctrl.$setViewValue(true); 
                        elem.removeClass('indeterminate');
                    } else if (setBoxes === 0) { 
                       ctrl.$setViewValue(false); 
                       elem.removeClass('indeterminate');
                    } else { 
                        ctrl.$setViewValue(false); 
                        elem.addClass('indeterminate');
                        elem.prop('indeterminate', true); 
                    }
                    ctrl.$render();
                }, true);
            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Misc.att:coachmark
 *
 * @description
 * <file src="src/coachmark/docs/readme.md" />
 *
 * @usage
 *
<button b2b-coachmark start-coachmark-callback="startCoachmark()" end-coachmark-callback="endCoachmark()" action-coachmark-callback="actionCoachmark(action)" coachmark-index="coachmarkIndex" coachmarks="coachmarkElements" id="coachmark0" class="btn btn-alt">Initiate tour</button>

 * @example
    <section id="code">   
        <b>HTML + AngularJS</b>
        <example module="b2b.att">
            <file src="src/coachmark/docs/demo.html" />
            <file src="src/coachmark/docs/demo.js" />
        </example>
    </section>
 */

angular.module('b2b.att.coachmark', ['b2b.att.utilities','b2b.att.position'])
    	
	.directive('b2bCoachmark', ['$document', '$compile', '$position', '$timeout', 'b2bViewport', 'keymap', function($document, $compile, $position, $timeout, b2bViewport, keymap) {
        return {
            restrict: 'A',
			 scope: {
				coachmarks: '=',
				coachmarkIndex: '=',
				startCoachmarkCallback: '&',
				endCoachmarkCallback: '&',
				actionCoachmarkCallback: '&'
			},
            link: function (scope, element, attrs, ctrl) {
				var coachmarkItems = scope.coachmarks;
				var body = $document.find('body').eq(0);
				var coackmarkJqContainer;
				var coackmarkContainer;
				var coachMarkElement;
				var backdropjqLiteEl;
				var coachmarkHighlight;
				var initaitedCoachmark = false;
				scope.coackmarkElPos ={
					'top':'',
					'left':''
				};
				
				scope.currentCoachmark = {};
				
				
                var coachmarkBackdrop = function(){
					backdropjqLiteEl = angular.element('<div class="b2b-modal-backdrop fade in hidden-by-modal"></div>');
					body.append(backdropjqLiteEl);

					backdropjqLiteEl.bind('click', function() {
						scope.closeCoachmark();
						scope.$apply();
					});
				};
				
				
				scope.closeButtonFocus = function(){
					if(document.getElementsByClassName('b2b-coachmark-header').length >0){
						document.getElementsByClassName('b2b-coachmark-header')[0].scrollLeft = 0;
						document.getElementsByClassName('b2b-coachmark-header')[0].scrollTop = 0;
					}
				}

				scope.actionCoachmark = function(action){
					scope.actionCoachmarkCallback({
						'action':action
					})
				};
				
				scope.closeCoachmark = function(){
					initaitedCoachmark = false;
					backdropjqLiteEl.remove();	
					coackmarkContainer.remove();
					coachmarkHighlight.remove();
					if(coachMarkElement !== undefined && coachMarkElement !==""){
						coachMarkElement.removeClass('b2b-coachmark-label')
					}
					if (angular.isFunction(scope.endCoachmarkCallback)){
						scope.endCoachmarkCallback();	
					}
					element[0].focus();
				}

				var realStyle = function(_elem, _style) {
				    var computedStyle;
				    if ( typeof _elem.currentStyle != 'undefined' ) {
				        computedStyle = _elem.currentStyle;
				    } else {
				        computedStyle = document.defaultView.getComputedStyle(_elem, null);
				    }

				    return _style ? computedStyle[_style] : computedStyle;
				};

				var copyComputedStyle = function(src, dest) {
				    var s = realStyle(src);
				    for ( var i in s ) {
				    	// Do not use `hasOwnProperty`, nothing will get copied
				        if ( typeof i == "string" && i != "cssText" && !/\d/.test(i) && i.indexOf('webkit') !== 0 ) {
				            // The try is for setter only properties
				            try {
				                dest.style[i] = s[i];
				                // `fontSize` comes before `font` If `font` is empty, `fontSize` gets
				                // overwritten.  So make sure to reset this property. (hackyhackhack)
				                // Other properties may need similar treatment
				                if ( i == "font" ) {
				                    dest.style.fontSize = s.fontSize;
				                }
				            } catch (e) {}
				        }
				    }
				};
				
				function showCoachmark(targetElement) {

					scope.currentCoachmark = targetElement;
					if(coachMarkElement !== undefined && coachMarkElement !==""){
						coachMarkElement.removeClass('b2b-coachmark-label')
						coackmarkContainer.remove();
						coachmarkHighlight.remove();
					}
					coachMarkElement = angular.element(document.querySelector(targetElement.elementId));
					
					var	elementPosition = $position.offset(coachMarkElement);
					
					coachmarkHighlight = angular.element('<div class="b2b-coachmark-highlight"></div><div class="b2b-coachmark-highlight b2b-coachmark-highlight-mask"></div>');
					coachmarkHighlight.css({
						'width': (elementPosition.width + 25) +'px',
						'top': (elementPosition.top -10) + 'px',
						'left': (elementPosition.left - 10) + 'px',
						'height': (elementPosition.height + 20) +'px'
					});
					if(targetElement.cloneHtml){
						var copy = coachMarkElement[0].cloneNode(true);
						copy.id = "b2b-unique-"+targetElement.elementId.slice(1);
						copyComputedStyle(coachMarkElement[0],copy);
						var copychildNodes = copy.childNodes;
						var coachmarkChildNodes = coachMarkElement[0].childNodes;
						for(i=0;i<copychildNodes.length;i++){
							if(copychildNodes[i].nodeType === '3'){
								copyComputedStyle(coachmarkChildNodes[i],copychildNodes[i])
							}
						}
						coachmarkHighlight[0].appendChild(copy); // IE11 only supports appendChild, not append
					}else{
						coachMarkElement.addClass('b2b-coachmark-label');
					}
					
					body.append(coachmarkHighlight);
					
					scope.coackmarkElPos.top = (elementPosition.top + elementPosition.height + 32) + 'px';
					scope.coackmarkElPos.left = (elementPosition.left - 158 + elementPosition.width / 2 ) + 'px';
					coackmarkJqContainer = angular.element('<div b2b-coachmark-container b2b-trap-focus-inside-element="true"></div>');
					coackmarkContainer = $compile(coackmarkJqContainer)(scope);
					body.append(coackmarkContainer);
					
					
					$timeout(function () {
						var currentCoachmarkContainer = document.getElementsByClassName('b2b-coachmark-container')[0];
						currentCoachmarkContainer.focus();

						newElem = angular.element(currentCoachmarkContainer);
						newElem.bind('keydown', function (e) {
							if(e.keyCode == keymap.KEY.TAB){
							    if(e.shiftKey) {
							    	if(e.target.className === 'b2b-coachmark-container'){
								        e.preventDefault();
								        e.stopPropagation();
								    }
							    }
							}
						});
						var coachmarkHeight = window.getComputedStyle(currentCoachmarkContainer).height.split('px')[0];
						var newOffsetHeight = Math.round(elementPosition.top) - elementPosition.height;
						
						// We need a slight offset to show the lightboxed item
						if(!targetElement.cloneHtml){
							TweenLite.to(window, 2, {scrollTo:{x: (scope.coackmarkElPos.left.split('px')[0] - 100), y: newOffsetHeight-200}});
						}
					}, 200);
				}
				
				element.bind('click', function (e) {
					initaitedCoachmark = true;
                    
					scope.$watch('coachmarkIndex', function () {
						if(initaitedCoachmark === true){
                            if(scope.coachmarkIndex === -1){
								scope.closeCoachmark();
							}else{
                                findAvailableCoachmark(scope.coachmarkIndex);
                                showCoachmark(scope.coachmarks[scope.coachmarkIndex]);
							}
						}
					});
					coachmarkBackdrop();
                    var findAvailableCoachmark = function(index){
                    	if(index === -1){
                    		scope.coachmarkIndex = 0;
                    	} else if(!angular.isDefined(scope.coachmarks[index]) || angular.element(document.querySelector(scope.coachmarks[index].elementId)).length === 0){
                            findAvailableCoachmark(index-1);
                        } else {
                            scope.coachmarkIndex = index;
                        }
                    }
					if (angular.isFunction(scope.startCoachmarkCallback)){
						scope.startCoachmarkCallback();	
					}
                    findAvailableCoachmark(scope.coachmarkIndex);
                    showCoachmark(scope.coachmarks[scope.coachmarkIndex]);
                    
					$document.bind('keydown', function (evt) {
						if (evt.which === 27 && initaitedCoachmark) {
							scope.closeCoachmark();
							scope.$apply();	
						}
					});
				});
				//performance technique to ensure scroll event doesn't cause lag
				var throttle = function(type, name, obj) {
			        obj = obj || window; 
			        var running = false; 
			        var func = function() { 
			            if (running) { return; } 
			            running = true; 
			             requestAnimationFrame(function() { 
			                obj.dispatchEvent(new CustomEvent(name)); 
			                running = false; 
			            }); 
			        }; 
			        obj.addEventListener(type, func); 
			    };
			 
			    scope.viewportWidth = b2bViewport.viewportWidth(); 
			    /* init - you can init any event */ 
			    throttle("resize", "optimizedResize"); 
			    window.addEventListener("optimizedResize", function() {
			    	if(initaitedCoachmark){
                        showCoachmark(scope.coachmarks[scope.coachmarkIndex]);
			        	scope.viewportWidth = b2bViewport.viewportWidth(); 
			        	scope.$digest();
			    	}
			    });
            }
        };
    }])
	.directive('b2bCoachmarkContainer', ['$document', '$position', function($document, $position) {
		return {
			restrict: 'A',
			transclude: true,
            replace: true,
			templateUrl: 'b2bTemplate/coachmark/coachmark.html',
			link: function (scope, element, attrs, ctrl) {
							
			}
		};	
	}]);
	

/** 
 * @ngdoc directive 
 * @name Template.att:Configuration Section 
 * 
 * @description 
 *  <file src="src/configurationSection/docs/readme.md" /> 
 * 
 * @example 
 *  <section id="code"> 
        <b>HTML + AngularJS</b> 
        <example module="b2b.att"> 
            <file src="src/configurationSection/docs/demo.html" /> 
            <file src="src/configurationSection/docs/demo.js" /> 
       </example> 
    </section>    
 * 
 */
angular.module('b2b.att.configurationSection', [])
  
/** 
 * @ngdoc directive 
 * @name Template.att:Directory Listing 
 * 
 * @description 
 *  <file src="src/directoryListingTemplate/docs/readme.md" /> 
 * 
 * @example 
 *  <section id="code"> 
        <b>HTML + AngularJS</b> 
        <example module="b2b.att"> 
            <file src="src/directoryListingTemplate/docs/demo.html" /> 
            <file src="src/directoryListingTemplate/docs/demo.js" /> 
       </example> 
    </section>    
 * 
 */
angular.module('b2b.att.directoryListingTemplate', [])
  
/**
 * @ngdoc directive
 * @name Forms.att:dropdowns
 *
 * @description
 *  <file src="src/dropdowns/docs/readme.md" />
 * @usage
 *
 * @example
   <section id="code">
    <example module="b2b.att">
     <file src="src/dropdowns/docs/demo.html" />
     <file src="src/dropdowns/docs/demo.js" />
    </example>
   </section>
 */
angular.module('b2b.att.dropdowns', ['b2b.att.utilities', 'b2b.att.position', 'ngSanitize'])

.constant('b2bDropdownConfig', {
    prev: '37,38',
    next: '39,40',
    menuKeyword: 'menu',
    linkMenuKeyword: 'link-menu',
    largeKeyword: 'large',
    smallKeyword: 'small'
})  

.directive("b2bDropdown", ['$timeout', '$compile', '$templateCache', 'b2bUserAgent', 'b2bDropdownConfig', '$position', function ($timeout, $compile, $templateCache, b2bUserAgent, b2bDropdownConfig, $position) {
    return {
        restrict: 'A',
        scope: true,
        require: 'ngModel',
        controller: ['$scope', '$element', '$attrs', function (scope, elem, attr) {
            scope.isInputDropdown = true;
            scope.placeHoldertext = attr.placeholderText;
            scope.containsSearch = true;
            if (angular.isDefined(attr.containsSearch)) {
                scope.containsSearch = attr.containsSearch;
            }
            if (attr.type) {
                if (attr.type.indexOf(b2bDropdownConfig.menuKeyword) > -1 || attr.type.indexOf(b2bDropdownConfig.linkMenuKeyword) > -1) {
                    scope.isInputDropdown = false;
                    if (attr.type.indexOf(b2bDropdownConfig.linkMenuKeyword) > -1) {
                        scope.dropdownType = b2bDropdownConfig.linkMenuKeyword;
                    } else if (attr.type.indexOf(b2bDropdownConfig.menuKeyword) > -1) {
                        scope.dropdownType = b2bDropdownConfig.menuKeyword;
                    }
                }
                if (attr.type.indexOf(b2bDropdownConfig.largeKeyword) > -1) {
                    scope.dropdownSize = b2bDropdownConfig.largeKeyword;
                } else if (attr.type.indexOf(b2bDropdownConfig.smallKeyword) > -1) {
                    scope.dropdownSize = b2bDropdownConfig.smallKeyword;
                }
            }

            scope.labelText = attr.labelText;

            scope.setBlur = function () {
                if(!scope.toggleFlag){
                    scope.setTouched();
                }
            };

            if ((scope.isInputDropdown && b2bUserAgent.notMobile()) || (!scope.isInputDropdown)) {
                var formCtrl = elem.controller('form');
                scope.setNgModelController = function (name, ngModelCtrl) {
                    if (name && formCtrl && ngModelCtrl) {
                        formCtrl[name] = ngModelCtrl;
                    }
                };
                scope.setOptionalCta = function (optionalCta) {
                    scope.optionalCta = optionalCta;
                };
                var innerHtml = angular.element('<div></div>').append(elem.html());
                innerHtml = ($compile(innerHtml)(scope)).html();
                var template = angular.element($templateCache.get('b2bTemplate/dropdowns/b2bDropdownDesktop.html'));
                template.find('ul').eq(0).append(innerHtml);
                template = $compile(template)(scope);
                elem.replaceWith(template);
            } else if (scope.isInputDropdown && b2bUserAgent.isMobile()) {
                elem.css({
                    'opacity': '0',
                    'filter': 'alpha(opacity=0)'
                });
                elem.addClass('awd-select isWrapped');
                elem.wrap('<span class="selectWrap"></span>');
                var cover = angular.element('<span aria-hidden="true"><i class="icon-down" aria-hidden="true"></i></span>');
                elem.parent().append(cover);
                elem.parent().append('<i class="icon-down" aria-hidden="true"></i>');
                var set = function () {
                    var sel = elem[0] ? elem[0] : elem;
                    var selectedText = "";
                    var selIndex = sel.selectedIndex;
                    if (typeof selIndex !== 'undefined') {
                        selectedText = sel.options[selIndex].text;
                    }
                    cover.text(selectedText).append('<i class="icon-down" aria-hidden="true"></i>');
                };
                var update = function (value) {
                    $timeout(set, 100);
                };

                if (attr.ngModel) {
                    scope.$watch(attr.ngModel, function (newVal, oldVal) {
                        update();
                    });
                }
                elem.bind('keyup', function (ev) {
                    if (ev.keyCode === keymap.KEY.TAB || ev.keyCode === keymap.KEY.ESC) {
                        return;
                    }
                    set();
                });
            }

        }],  
        link: function (scope, elem, attr, ctrl) {
            if ((scope.isInputDropdown && b2bUserAgent.notMobile()) || (!scope.isInputDropdown)) {
                scope.updateModel = function () {
                    ctrl.$setViewValue(scope.currentSelected.value);
                    if (scope.dropdownRequired && scope.currentSelected.value === '') {
                        scope.setRequired(false);
                    } else {
                        scope.setRequired(true);
                    }

                    if (scope.dropdownType === b2bDropdownConfig.linkMenuKeyword) {
                        $timeout(function () {
                            scope.appendCaretPositionStyle();
                        }, 100);
                    }
                };
                ctrl.$render = function () {

                $timeout(function () {

                        if ((angular.isUndefined(ctrl.$viewValue) || ctrl.$viewValue == '') && (angular.isUndefined(scope.placeHoldertext) || scope.placeHoldertext == '')) {
                            scope.dropdownLists[ctrl.$viewValue] && scope.dropdownLists[ctrl.$viewValue][0].updateDropdownValue();
                        } else if ((angular.isUndefined(scope.placeHoldertext) || scope.placeHoldertext == '') && ctrl.$viewValue !== '' ) {
                            scope.dropdownLists[ctrl.$viewValue] && scope.dropdownLists[ctrl.$viewValue][0].updateDropdownValue();
                        } else if ((angular.isUndefined(ctrl.$viewValue) || ctrl.$viewValue == '') && scope.placeHoldertext !== '' )  {
                            scope.currentSelected.text = scope.placeHoldertext;
                            ctrl.$setViewValue(scope.placeHoldertext);
                        } else {
                            scope.dropdownLists[ctrl.$viewValue] && scope.dropdownLists[ctrl.$viewValue][0].updateDropdownValue();
                        }

                    }, 100);
                };

                scope.disabled = false;
                scope.dropdownName = attr.name;
                scope.dropdownId = attr.id;
                scope.labelId = attr.ariaLabelledby;
                scope.dropdownDescribedBy = attr.ariaDescribedby;
                if (attr.required) {
                    scope.dropdownRequired = true;
                } else {
                    scope.dropdownRequired = false;
                }
                elem.removeAttr('name');
                elem.removeAttr('id');
                scope.$parent.$watch(attr.ngDisabled, function (val) {
                    scope.disabled = val;
                });
            }
        }
    };
}])

.directive("b2bDropdownToggle", ['$document', '$documentBind', '$isElement', 'b2bDropdownConfig', 'keymap', 'b2bUtilitiesConfig', '$timeout', '$position', function ($document, $documentBind, $isElement, b2bDropdownConfig, keymap, b2bUtilitiesConfig, $timeout, $position) {
    return {
        restrict: 'A',
        require: '?^b2bKey',
        link: function (scope, elem, attr, ctrl) {
            scope.appendCaretPositionStyle = function () {
                while (document.querySelector('style.b2bDropdownCaret')) {
                    document.querySelector('style.b2bDropdownCaret').remove();
                };
                var caretPosition = $position.position(elem).width - 26;
                if (scope.dropdownType === b2bDropdownConfig.linkMenuKeyword) {
                    var template = angular.element('<style class="b2bDropdownCaret" type="text/css">.linkSelectorModule .active+.moduleWrapper:before {left: ' + caretPosition + 'px;}</style>');
                    $document.find('head').append(template);
                }
            };

            if (scope.isInputDropdown && (scope.labelText !== undefined)) {
                elem.attr('aria-label', scope.labelText);
            }

            scope.toggleFlag = false;
            scope.dropdownLists = {};
            scope.dropdownListValues = [];
            scope.dropdown = {
                totalIndex: -1
            };
            scope.currentSelected = {
                value: '',
                text: '',
                label: '',
                index: -1
            };
            scope.dropdownTextList = [];
            var searchString = '';

            scope.removeItem = function(value){
                delete scope.dropdownLists[value];
                var index = scope.dropdownListValues.indexOf(value);
                scope.dropdownListValues.splice(index,1);
                scope.dropdownTextList=[];
                scope.dropdown.totalIndex = scope.dropdownListValues.length-1; 
            };
            var getDropdownText = function(){
                var dropdownItems = elem.parent().find('ul').children();
                var count = dropdownItems.length;
                for(var i=0;i<count;i++){
                    scope.dropdownTextList.push(dropdownItems.eq(i).text());
                }
            };
            var searchElement = function (searchExp) {
                 if(scope.dropdownTextList.length ==0){
                    getDropdownText ();
                }
                var regex = new RegExp("\\b" + searchExp, "gi");
                if(scope.containsSearch === "false"){
                    regex = new RegExp("^" + searchExp, "i");
                }
                var position = scope.dropdownTextList.regexIndexOf(regex, scope.currentSelected.index + 1, true);
                if (position > -1) {
                    return position;
                }
                return undefined;
            };
            var startTimer = function (time) {
                if (searchString === '') {
                    $timeout(function () {
                        searchString = '';
                    }, time);
                }
            };
            scope.toggleDropdown = function (toggleFlag) {
                if (!scope.disabled) {
                    if (angular.isDefined(toggleFlag)) {
                        scope.toggleFlag = toggleFlag;
                    } else {
                        scope.toggleFlag = !scope.toggleFlag;
                    }
                    if (!scope.toggleFlag) {
                        if (scope.isInputDropdown) {
                            elem.parent().find('input')[0].focus();
                        } else {
                            elem.parent().find('button')[0].focus();
                        }
                        scope.setTouched();
                    } else {
                        scope.dropdown.highlightedValue = scope.currentSelected.value;
                        if (ctrl && ctrl.enableSearch) {
                            if (angular.isDefined(scope.dropdownLists[scope.currentSelected.value])) {
                                ctrl.resetCounter(scope.dropdownLists[scope.currentSelected.value][2]);
                            }
                        }
                        $timeout(function () {
                            if(scope.dropdownLists[scope.currentSelected.value] !== undefined){
                                (scope.dropdownLists[scope.currentSelected.value][1])[0].focus();
                            } else {
                                if (scope.isInputDropdown) {
                                    elem.parent().find('input')[0].focus();
                                } else {
                                    elem.parent().find('button')[0].focus();
                                }
                            }
                        }, 100);
                        if (scope.dropdownType === b2bDropdownConfig.linkMenuKeyword) {
                            scope.appendCaretPositionStyle();
                        }
                    }
                }
            };

            elem.bind('keydown', function (ev) {
                if (!ev.keyCode) {
                    if (ev.which) {
                        ev.keyCode = ev.which;
                    } else if (ev.charCode) {
                        ev.keyCode = ev.charCode;
                    }
                }
                if (!scope.toggleFlag) {
                    if (ev.keyCode) {
                        var currentIndex = scope.currentSelected.index;
                        if (ev.keyCode === keymap.KEY.DOWN) {
                            scope.toggleDropdown(true);
                            ev.preventDefault();
                            ev.stopPropagation();
                        } else if (b2bDropdownConfig.prev.split(',').indexOf(ev.keyCode.toString()) > -1) {
                            angular.isDefined(scope.dropdownListValues[currentIndex - 1]) ? scope.dropdownLists[scope.dropdownListValues[currentIndex - 1]][0].updateDropdownValue() : angular.noop();
                            ev.preventDefault();
                            ev.stopPropagation();
                        } else if (b2bDropdownConfig.next.split(',').indexOf(ev.keyCode.toString()) > -1) {
                            angular.isDefined(scope.dropdownListValues[currentIndex + 1]) ? scope.dropdownLists[scope.dropdownListValues[currentIndex + 1]][0].updateDropdownValue() : angular.noop();
                            ev.preventDefault();
                            ev.stopPropagation();
                        } else if (ev.keyCode >= 48 && ev.keyCode <= 105) {
                            startTimer(b2bUtilitiesConfig.searchTimer);
                            searchString = searchString + (keymap.MAP[ev.keyCode] || '');
                            var position = searchElement(searchString);
                            angular.isDefined(scope.dropdownListValues[position]) ? scope.dropdownLists[scope.dropdownListValues[position]][0].updateDropdownValue() : angular.noop();
                            ev.preventDefault();
                            ev.stopPropagation();
                        }
                    }
                } else {
                    if (ev.altKey === true && ev.keyCode === keymap.KEY.UP) {
                        scope.toggleDropdown(false);
                        ev.preventDefault();
                        ev.stopPropagation();
                    } else if (ev.keyCode === keymap.KEY.TAB || ev.keyCode === keymap.KEY.ESC) {
                        scope.toggleDropdown(false);
                        ev.preventDefault();
                        ev.stopPropagation();
                    }
                }
                scope.$apply(); // TODO: Move this into each block to avoid expensive digest cycles
            });
            var outsideClick = function (e) {
                var isElement = $isElement(angular.element(e.target), elem.parent(), $document);
                if (!isElement) {
                    scope.toggleDropdown(false);
                    scope.$apply();
                }
            };
            $documentBind.click('toggleFlag', outsideClick, scope);
            $documentBind.touch('toggleFlag', outsideClick, scope);
        }
    };
}])

.directive("b2bDropdownGroup", ['$compile', '$templateCache', 'b2bUserAgent', function ($compile, $templateCache, b2bUserAgent) {
    return {
        restrict: 'A',
        controller: ['$scope', '$element', '$attrs', function (scope, elem, attr) {
            if ((scope.isInputDropdown && b2bUserAgent.notMobile()) || (!scope.isInputDropdown)) {
                var innerHtml = angular.element('<div></div>').append(elem.html());
                innerHtml = ($compile(innerHtml)(scope)).html();
                var template = angular.element($templateCache.get('b2bTemplate/dropdowns/b2bDropdownGroupDesktop.html'));
                template.attr('ng-repeat', attr.optGroupRepeat);
                template.attr('label', elem.attr('label'));
                template.find('ul').append(innerHtml);
                elem.replaceWith(template);
            } else if (scope.isInputDropdown && b2bUserAgent.isMobile()) {
                var template = angular.element(elem.prop('outerHTML'));
                template.attr('ng-repeat', attr.optGroupRepeat);
                template.removeAttr('b2b-dropdown-group');
                template.removeAttr('opt-group-repeat');
                template = $compile(template)(scope);
                elem.replaceWith(template);
            }
        }]
    };
}])

.directive("b2bDropdownGroupDesktop", [function () {
    return {
        restrict: 'A',
        scope: true,
        link: function (scope, elem, attr, ctrl) {
            scope.groupHeader = attr.label;
        }
    };
}])

.directive("b2bDropdownList", ['$compile', '$templateCache', 'b2bUserAgent', function ($compile, $templateCache, b2bUserAgent) {
    return {
        restrict: 'A',
        controller: ['$scope', '$element', '$attrs', function (scope, elem, attr) {           
            if ((scope.isInputDropdown && b2bUserAgent.notMobile()) || (!scope.isInputDropdown)) {
                var innerHtml = angular.element('<div></div>').append(elem.html());
                innerHtml = ($compile(innerHtml)(scope)).html();
                var template = angular.element($templateCache.get('b2bTemplate/dropdowns/b2bDropdownListDesktop.html'));
                template.attr('ng-repeat', attr.optionRepeat);
                template.attr('value', elem.attr('value'));
                template.attr('search-key', elem.text());
                if (elem.attr('aria-describedby')){
                    template.attr('aria-describedby', attr.ariaDescribedby);
                }
                if (elem.attr('imgsrc')) {
                    if (elem.attr('imgalt')) {
                        template.append('<img role="presentation" ng-src="' + elem.attr('imgsrc') + '" alt="' + elem.attr('imgalt') + '"/>');
                    } else {
                        template.append('<img role="presentation" ng-src="' + elem.attr('imgsrc') + '" alt=""/>');
                    }
                }
                template.append(innerHtml);
                elem.replaceWith(template);
            } else if (scope.isInputDropdown && b2bUserAgent.isMobile()) {
                var template = angular.element(elem.prop('outerHTML'));
                template.attr('ng-repeat', attr.optionRepeat);
                if (elem.attr('aria-describedby')){
                    template.attr('aria-describedby', attr.ariaDescribedby);
                }
                template.removeAttr('b2b-dropdown-list');
                template.removeAttr('option-repeat');
                template = $compile(template)(scope);
                elem.replaceWith(template);
            }
        }]
    };
}])

.directive("b2bDropdownListDesktop", ['$sce', 'keymap', 'b2bDropdownConfig', function ($sce, keymap, b2bDropdownConfig) {
    return {
        restrict: 'A',
        scope: true,

        link: function (scope, elem, attr, ctrl) {
            var dropdownListValue = scope.dropdownListValue = attr.value;
            scope.dropdown.totalIndex++;
            var dropdownListIndex = scope.dropdown.totalIndex;
            scope.dropdownListValues.push(dropdownListValue);
            scope.dropdownLists[dropdownListValue] = [];
            scope.dropdownLists[dropdownListValue][0] = scope;
            scope.dropdownLists[dropdownListValue][1] = elem;
            scope.dropdownLists[dropdownListValue][2] = dropdownListIndex;
            scope.$parent.$parent.dropdownTextList=[];                                
            scope.updateDropdownValue = function () {
                scope.currentSelected.value = dropdownListValue;
                if (scope.isInputDropdown) {
                    scope.currentSelected.text = elem.text();
                    scope.currentSelected.label = elem.text();
                } else if ((scope.dropdownType === b2bDropdownConfig.linkMenuKeyword) || (scope.dropdownType === b2bDropdownConfig.menuKeyword && scope.dropdownSize === b2bDropdownConfig.smallKeyword)) {
                    scope.currentSelected.text = dropdownListValue;
                    scope.currentSelected.label = dropdownListValue;
                } else if (scope.dropdownType === b2bDropdownConfig.menuKeyword) {
                    scope.currentSelected.text = $sce.trustAsHtml(elem.html());
                    scope.currentSelected.label = elem.text();
                }
                scope.currentSelected.index = dropdownListIndex;
                scope.updateModel();
            };
            scope.selectDropdownItem = function () {
                scope.setDirty();
                scope.updateDropdownValue();
                scope.toggleDropdown(false);
            };
            scope.highlightDropdown = function () {
                scope.dropdown.highlightedValue = dropdownListValue;
            };
            elem.bind('mouseover', function (ev) {
                elem[0].focus();
            });

            elem.bind('keydown', function (ev) {
                if (!ev.keyCode) {
                    if (ev.which) {
                        ev.keyCode = ev.which;
                    } else if (ev.charCode) {
                        ev.keyCode = ev.charCode;
                    }
                }
                if (ev.altKey === true && ev.keyCode === keymap.KEY.UP) {
                    scope.toggleDropdown(false);
                    ev.preventDefault();
                    ev.stopPropagation();
                } else if (ev.keyCode === keymap.KEY.TAB || ev.keyCode === keymap.KEY.ESC) {
                    scope.toggleDropdown(false);
                    ev.preventDefault();
                    ev.stopPropagation();
                }
                scope.$apply();
            });
            scope.$on('$destroy',function(){
                scope.removeItem(dropdownListValue);
            });
        }
    };
}])

.directive("b2bDropdownRepeat", ['$compile', 'b2bUserAgent', function ($compile, b2bUserAgent) {
    return {
        restrict: 'A',
        controller: ['$scope', '$element', '$attrs', function (scope, elem, attr) {
            if ((scope.isInputDropdown && b2bUserAgent.notMobile()) || (!scope.isInputDropdown)) {
                var innerHtml = angular.element('<div></div>').append(elem.html());
                innerHtml = ($compile(innerHtml)(scope)).html();
                var template = angular.element('<div></div>');
                template.attr('ng-repeat', attr.b2bDropdownRepeat);
                template.append(innerHtml);
                elem.replaceWith(template);
            } else if (scope.isInputDropdown && b2bUserAgent.isMobile()) {
                angular.noop();
            }
        }]
    };
}])

.directive("b2bDropdownValidation", ['$timeout', function ($timeout) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, elem, attr, ctrl) {
            $timeout(function () {
                scope.setNgModelController(attr.name, ctrl);
            }, 100);
            scope.setDirty = function () {
                if (ctrl.$dirty === false) {
                    ctrl.$dirty = true;
                    ctrl.$pristine = false;
                }
            };
            scope.setTouched = function () {
                ctrl.$touched2 = true;
                ctrl.$pristine = false;
            };
            scope.setRequired = function (flag) {
                ctrl.$setValidity('required', flag);
            };
        }
    };
}])

.directive('b2bDropdownOptionalCta', [function () {
    return {
        restrict: 'EA',
        transclude: true,
        replace: true,
        template: '',
        compile: function (elem, attr, transclude) {
            return function link(scope, elem, attr, ctrl) {
                if (scope.setOptionalCta) {
                    scope.setOptionalCta(transclude(scope, function () {}));
                }
                elem.remove();
            };
        }
    };
}]);
/**
 * @ngdoc directive
 * @name Forms.att:File Upload
 *
 * @description
 *  <file src="src/fileUpload/docs/readme.md" />
 *
 * @usage
 * 
<form id="dragDropFile">		
    <div b2b-file-drop file-model="fileModel" on-drop="triggerFileUpload()"  align="center">
        <p>
            <br>To upload a file, drag & drop it here or 
			<span b2b-file-link file-model="fileModel" on-file-select="triggerFileUpload()" >
				click here to select from your computer.
			</span><br>
        </p>
    </div>
</form>
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/fileUpload/docs/demo.html" />
            <file src="src/fileUpload/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.fileUpload', ['b2b.att.utilities'])
    .directive('b2bFileDrop', [function() {
            return {
                restrict: 'EA',
                scope: {
                    fileModel: '=',
                    onDrop: '&'
                },
				controller: ['$scope', '$attrs', function($scope, $attrs) {
                    this.onDrop = $scope.onDrop;
                }],
                link: function(scope, element) {
                    element.addClass('b2b-dragdrop');
                    element.bind(
                        'dragover',
                        function(e) {
                            if (e.originalEvent) {
                                e.dataTransfer = e.originalEvent.dataTransfer;
                            }
                            e.dataTransfer.dropEffect = 'move';
                            // allows us to drop
                            if (e.preventDefault) {
                                e.preventDefault();
                            }
                            element.addClass('b2b-dragdrop-over');
                            return false;
                        }
                    );
                    element.bind(
                        'dragenter',
                        function(e) {
                            // allows us to drop
                            if (e.preventDefault) {
                                e.preventDefault();
                            }
                            element.addClass('b2b-dragdrop-over');
                            return false;
                        }
                    );
                    element.bind(
                        'dragleave',
                        function() {
                            element.removeClass('b2b-dragdrop-over');
                            return false;
                        }
                    );
                    element.bind(
                        'drop',
                        function(e) {
                            // Stops some browsers from redirecting.
                            if (e.preventDefault) {
                                e.preventDefault();
                            }
                            if (e.stopPropagation) {
                                e.stopPropagation();
                            }
                            if (e.originalEvent) {
                                e.dataTransfer = e.originalEvent.dataTransfer;
                            }
                            element.removeClass('b2b-dragdrop-over');
                            if (e.dataTransfer.files && e.dataTransfer.files.length > 0) {
                                scope.fileModel = e.dataTransfer.files[0];
                                scope.$apply();
                                if (angular.isFunction(scope.onDrop)) {
                                    scope.onDrop();
                                }
                            }
                            return false;
                        }
                    );
                }
            };
        }])
        .directive('b2bFileLink', [function() {
            return {
                restrict: 'EA',
                require: '^?b2bFileDrop',
                replace: true,
                transclude: true,
                templateUrl: 'b2bTemplate/fileUpload/fileUpload.html',
                scope: {
                    fileModel: '=?',
                    onFileSelect: '&'
                },
                controller: ['$scope', function($scope) {
                    this.setFileModel = function(fileModel) {
                        if ($scope.takeFileModelFromParent) {
                            $scope.$parent.fileModel = fileModel;
                            $scope.$parent.$apply();
                        } else {
                            $scope.fileModel = fileModel;
                            $scope.$apply();
                        }
                    };
                    this.callbackFunction = function() {
                        if (angular.isFunction($scope.onFileSelect)) {
                            $scope.onFileSelect();
                        }
                    };
		
                }],
                link: function(scope, element, attr, b2bFileDropCtrl) {
                    scope.takeFileModelFromParent = false;
                    if (!(attr.fileModel) && b2bFileDropCtrl) {
                        scope.takeFileModelFromParent = true;
                    }
                    if (!(attr.onFileSelect) && b2bFileDropCtrl) {
                        scope.onFileSelect = b2bFileDropCtrl.onDrop;
                    }
                }
            };
        }])
        .directive('b2bFileChange', ['$log', '$rootScope', function($log, $rootScope) {
            return {
                restrict: 'A',
                require: '^b2bFileLink',
                link: function(scope, element, attr, b2bFileLinkCtrl) {
                    element.bind('change', changeFileModel);

                    function changeFileModel(e) {
                        if (e.target.files && e.target.files.length > 0) {
                            b2bFileLinkCtrl.setFileModel(e.target.files[0]);
                            b2bFileLinkCtrl.callbackFunction();
                        } else {
                            var strFileName = e.target.value;
                            try {
                                var objFSO = new ActiveXObject("Scripting.FileSystemObject");
                                b2bFileLinkCtrl.setFileModel(objFSO.getFile(strFileName));
                                b2bFileLinkCtrl.callbackFunction();
                            } catch (e) {
                                var errMsg = "There was an issue uploading " + strFileName + ". Please try again.";
                                $log.error(errMsg);
                                $rootScope.$broadcast('b2b-file-link-failure', errMsg);
                            }
                        }
                    }
                }
            };
        }]);
/**
 * @ngdoc directive
 * @name Banners, marquees & tiles.b2b:filmstrip
 *
 * @description
 *  <file src="src/filmstrip/docs/readme.md" />
 * @usage
 *  <div b2b-filmstrip filmstrip-type="{{filmstripType1}}">
		<div b2b-filmstrip-content ng-repeat="item in items1">
			<a ng-href="{{item.linkUrl}}" class="fs-items">
				<img ng-src="{{item.imageUrl}}" class="fs-item-image" alt="{{item.altText}}">
				<div class="fs-item-desc">
					<p class="fs-icon-label" ng-bind="item.label">
						<span class="hidden-spoken">.</span>
					</p>
				</div>
			</a>
		</div>
		<!-- extra link to full list -->
		<div b2b-filmstrip-content>
			<a class="fs-items view-more" ng-href="{{categoryLink1}}">View more</a>
		</div>
	</div>
 *
 * @example
 <section id="code">
 <b>HTML + AngularJS</b>
 <example module="b2b.att">
 <file src="src/filmstrip/docs/demo.html" />
 <file src="src/filmstrip/docs/demo.js" />
 </example>
 </section>
 */
angular.module('b2b.att.filmstrip', ['b2b.att.position', 'b2b.att.utilities', 'ds2Scroll'])
        .directive('b2bFilmstrip', ['$document', '$window', '$timeout', 'keymap', 'b2bUserAgent', function ($document, $window, $timeout, keymap, userAgent) {
                return {
                    restrict: 'EA',
                    transclude: true,
                    replace: true,
                    scope: {
                        filmstripType: '@',
                        filmstripHeading: '@'
                    },
                    templateUrl: 'b2bTemplate/filmstrip/b2bFilmstrip.html',
                    compile: function (elem, attr) {
                        return this.link;
                    },
                    controller: ['$scope', '$timeout', '$position', '$element', '$window', function ($scope, $timeout, $position, $element, $window) {
                            this.groups = [];
                            this.index = 0;

                            this.addGroup = function (groupScope) {
                                var that = this;
                                groupScope.index = this.groups.length;
                                groupScope.focused = false;
                                this.groups.push(groupScope);
                                groupScope.$on('$destroy', function () {
                                    that.removeGroup(groupScope);
                                });
                                return groupScope.index;
                            };

                            this.removeGroup = function (group) {
                                var index = this.groups.indexOf(group);
                                if (index !== -1) {
                                    this.groups.splice(this.groups.indexOf(group), 1);
                                }
                            };

                            this.registerElement = function (elem) {
                                $scope.iwidth = elem[0].clientWidth;
                                var window = angular.element($window);
                                var showFilmstripContent = function () {
                                    if ($position.isElementInViewport($element)) {
                                        $timeout(function () {																				
                                            var container = $element[0].querySelector(".contents");
											angular.element(container).addClass('items-in');											
											/*enable right arrow button only when container has scrollable content*/
											if(container.scrollWidth > container.clientWidth){
												angular.element($element[0].querySelector('.right-arrow')).attr('disabled', null);
											}											
                                        }, 100);
                                        $timeout(function () {
                                            window.unbind('scroll', showFilmstripContent);
                                            window.unbind('orientationchange', showFilmstripContent);
                                            window.unbind('resize', showFilmstripContent);
                                        });
                                    }
                                    $scope.$apply();
                                };
                                window.bind('scroll', showFilmstripContent);
                                window.bind('orientationchange', showFilmstripContent);
                                window.bind('resize', showFilmstripContent);
                                $timeout(showFilmstripContent, 500);
                            };

                            this.cycle = function (group, down, noRecycle) {
                                if (!down) {
                                    if (this.index <= 0 && !noRecycle) {
                                        this.index = this.groups.length - 1;
                                    } else {
                                        this.index--;
                                    }
                                } else {
                                    if (this.index >= (this.groups.length - 1) && !noRecycle) {
                                        this.index = 0;
                                    } else {
                                        this.index++;
                                    }
                                }
                                group.focused = false;
                                this.groups[this.index].focused = true;
                                $scope.$apply();
                            };

                            this.setId = function (id) {
                                $scope.cfsId = id;
                            };

                            this.getId = function () {
                                return $scope.cfsId;
                            };
                        }],
                    link: function (scope, elem, attr, ctrl) {
                        var fsContent = elem[0].querySelector('.contents'),
						rightArrow = elem[0].querySelector('.right-arrow'),
						leftArrow = elem[0].querySelector('.left-arrow');								
						if(fsContent.scrollLeft === 0){						
							angular.element(leftArrow).attr('disabled', true);
							angular.element(rightArrow).attr('disabled', true);
						};
                        scope.fsId = attr.id ? attr.id : "";
                        ctrl.setId(scope.fsId);
                        scope.count = 0;
                        scope.isMobile = userAgent.isMobile();
						fsContent.addEventListener('scroll',function(e){							
							var csl = e.currentTarget.scrollLeft;
							var msl = (e.currentTarget.scrollWidth - e.currentTarget.offsetWidth);
							if( csl === 0){
								angular.element(leftArrow).attr('disabled', true);
								angular.element(rightArrow).attr('disabled', null);
							}
							else if( csl > 0 & csl < msl){
								angular.element(leftArrow).attr('disabled', null);
								angular.element(rightArrow).attr('disabled', null);
							}
							else if((msl - csl) < 5){
								angular.element(leftArrow).attr('disabled', null);
								angular.element(rightArrow).attr('disabled', true);
							}
						});			
						fsContent.addEventListener('dragstart', function(e) { 
							e.preventDefault();
						});						
                        var viewerWidth = function (){									
							item_width = fsContent.querySelectorAll('.item')[0].clientWidth;
							scroll_unit = itemQty(fsContent);	
							return (item_width * scroll_unit);
                        };                        
                        scope.moveright = function (){																		
							var offsetL = fsContent.scrollLeft + viewerWidth();
							angular.element(fsContent).eq(0).scrollTo(offsetL, 0, 800);
						};                        
						scope.moveleft = function (){						
							var offsetL = fsContent.scrollLeft - viewerWidth();
							angular.element(fsContent).eq(0).scrollTo(offsetL, 0, 800);
                        };					
                        // returns the number of visible items in the filmstrip
                        var itemQty = function (obj){
                            var item = obj.querySelectorAll('.item')[1],
                            width = item.offsetWidth + 25;
                            return Math.floor(obj.clientWidth / width);
                        };                       
                        elem.on('keydown', function (e){
                            var code = e.keyCode,
                                    RIGHT = 39 === code, LEFT = 37 === code, SPACE = 32 === code, ENTER = 13 === code,
                                    tar = angular.element(e.target);
                            // if left or right arrow on the keyboard is pressed
                            if (LEFT || RIGHT) {
                                e.preventDefault()
                                // if focus on ul.contents, go to first list item
                                if (tar.hasClass('contents')) 
                                {
                                    var first = tar.children(':first-child')
                                    tar.attr({
                                        'tabindex': '-1',
                                        'aria-activedescendant': first.attr('id')
                                    })
                                    first.attr({
                                        'tabindex': '0',
                                        'aria-selected': 'true'
                                    }).focus()
                                } else if (tar.hasClass('item') || e.target.tagName.toLowerCase === 'a') {
                                    var li = e.target.tagName.toLowerCase === 'a' ? e.target.parentElement : e.target;
                                    var item;
                                    // skip hidden item
                                    if (RIGHT && li.nextElementSibling) {
                                        if (window.getComputedStyle(li.nextElementSibling).display === "none") {
                                            item = li.nextElementSibling.nextElementSibling;
                                        } else {
                                            item = li.nextElementSibling;
                                        }
                                        ctrl.index++;
                                        if(ctrl.index % itemQty(fsContent) === 0){
                                            scope.count++;
                                            angular.element(leftArrow).attr('disabled', null);
                                        }                                        
                                    } else if (LEFT && li.previousElementSibling) {
                                        if (window.getComputedStyle(li.previousElementSibling).display === "none") {
                                            item = li.previousElementSibling.previousElementSibling;
                                        } else {
                                            item = li.previousElementSibling;
                                        }
                                        ctrl.index--;
                                        if(ctrl.index % itemQty(fsContent) === 0){
                                            scope.count--;
                                        }
                                    }
                                    if (angular.element(item).length && window.getComputedStyle(item).display !== "none") {
                                        angular.element(li).attr({
                                            'tabindex': '-1',
                                            'aria-selected': 'false'
                                        }).parent().attr('aria-activedescendant', angular.element(item).attr('id'));

                                        angular.element(item).attr({
                                            'tabindex': '0',
                                            'aria-selected': 'true'
                                        });
                                        angular.element(item)[0].focus();
                                    }
                                }
                            }
                            else if ((ENTER || SPACE) && tar.hasClass('item')) {
                                e.preventDefault();
                                tar.children('a')[0].click();
                            }
                        });                    									
						if(!userAgent.isMobile()) {														
							scope.mouseEvent={};
							angular.element(fsContent).on('mousedown', function(e){							
								if(!!fsContent){																
									scope.mouseEvent.mouseDown = true
									scope.mouseEvent.pageX = e.pageX
									scope.mouseEvent.pageY = e.pageY
								}							
							});
							
							$document.on('mouseup', function(e){							
								// check only mouseDown is set
								if(scope.mouseEvent.mouseDown) {               
								   // if mouse motion acting as drag, stop click
								   if(scope.mouseEvent.trace && scope.mouseEvent.trace.length > 3){									  
										e.preventDefault(); 									  
								   }					   
								   // reset variables 
								   scope.mouseEvent.mouseDown = false
								   scope.mouseEvent.trace = scope.mouseEvent.fsc = scope.mouseEvent.pageX = scope.mouseEvent.pageY = undefined
								}
												
							});							
							
							elem.on('mousemove', function(e){							
								if(scope.mouseEvent.mouseDown) {
								   // recording the delta of movements
								   scope.mouseEvent.movementX = e.pageX - scope.mouseEvent.pageX;
								   scope.mouseEvent.movementY = e.pageY - scope.mouseEvent.pageY;
								   scope.mouseEvent.pageX = e.pageX;
								   scope.mouseEvent.pageY = e.pageY;
								   var moved = scope.mouseEvent.movementX;
								   scope.mouseEvent.fsc = {};
								   scope.mouseEvent.fsc.tar = elem[0].querySelector('.contents');
								   scope.mouseEvent.fsc.scrolled = fsContent.scrollLeft || 0;
								   fsContent.scrollLeft = (scope.mouseEvent.fsc.scrolled - moved);
								   //scope.mouseEvent.fsc.tar.scrollLeft(scope.mouseEvent.fsc.scrolled - moved);
								   // a variable to track if mouse travel far enough to be determine as drag/swipe
								   scope.mouseEvent.trace = scope.mouseEvent.trace || [];
								   scope.mouseEvent.trace.push(scope.mouseEvent.movementX);
								}
							});		
							
						}
						
						
					 }
                };
            }])
        .directive('b2bFilmstripContent', ['$timeout', 'keymap', function ($timeout, keymap) {
                return {
                    restrict: 'EA',
                    transclude: true,
                    replace: true,
                    scope: {},
                    require: '^b2bFilmstrip',
                    templateUrl: 'b2bTemplate/filmstrip/b2bFilmstripContent.html',
                    link: function (scope, elem, attr, ctrl) {
                        ctrl.registerElement(elem);
                        scope.cFsId = ctrl.getId();
                        scope.isSelected = false;
                        scope.fsIndex = ctrl.addGroup(scope);
                        elem.css('left','0px');

                        if (scope.$parent.$first) {
                            elem.attr('tabindex', 0);
                        }
                        else {
                            elem.attr('tabindex', -1);
                        }

                        scope.$watch("focused", function (value) {
                            if (!!value) {
                                $timeout(function () {
                                    elem[0].focus();
                                    scope.isSelected = true;
                                }, 0);
                            }
                            else
                            {
                                scope.isSelected = false;
                            }
                        });

                    }
                };
            }])
		.directive('b2bFilmstripViewmore', ['$timeout', function($timeout) {
			return {
				restrict: 'A',
				transclude: false,		
				link: function (scope, elem, attr) {
                    elem.bind('click', function(){
                        var nextElement = elem.next();
                        $timeout(function () {
                            if(nextElement.length > 0){
                                nextElement[0].focus();
                            }
                        }, 0);			
						elem.remove();
					});
				}
			};		
		}]);

/**
 * @ngdoc directive
 * @name Navigation.att:filters
 *
 * @description
 *  <file src="src/filters/docs/readme.md" />
 *
 * @usage
 *  <div b2b-filters></div>
 *
 * @example
 * 	<section id="code">
	   <b>HTML + AngularJS</b>
	    <example module="b2b.att">
            <file src="src/filters/docs/demo.html" />
            <file src="src/filters/docs/demo.js" />
       </example>
	</section>
 * 
 */
angular.module('b2b.att.filters', ['b2b.att.utilities', 'b2b.att.multipurposeExpander'])
    .filter('filtersSelectedItemsFilter', [function () {
        return function (listOfItemsArray) {

            if (!listOfItemsArray) {
                listOfItemsArray = [];
            }

            var returnArray = [];

            for (var i = 0; i < listOfItemsArray.length; i++) {
                for (var j = 0; j < listOfItemsArray[i].filterTypeItems.length; j++) {
                    if (listOfItemsArray[i].filterTypeItems[j].selected && !listOfItemsArray[i].filterTypeItems[j].inProgress) {
                        returnArray.push(listOfItemsArray[i].filterTypeItems[j]);
                    }
                }
            }

            return returnArray;
        };
    }]);
/**
 * @ngdoc directive
 * @name Messages, modals & alerts.att:flyout
 *
 * @description
 *  <file src="src/flyout/docs/readme.md" />
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/flyout/docs/demo.html" />
            <file src="src/flyout/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.flyout', ['b2b.att.utilities', 'b2b.att.position'])
    .directive('b2bFlyout', ['$timeout', 'b2bDOMHelper', 'keymap', 'events', function ($timeout, b2bDOMHelper, keymap, events) {
        return {
            restrict: 'EA',
            transclude: true,
            templateUrl: 'b2bTemplate/flyout/flyout.html',
            controller: ['$scope', '$element', '$attrs', function (scope, elem, attr) {
                scope.flyoutOpened = false;
                var contentScope = '';
                var togglerScope = '';
                this.registerContentScope = function (scp) {
                    contentScope = scp;
                };
                this.registerTogglerScope = function (scp) {
                    togglerScope = scp;
                };

                this.toggleFlyoutState = function () {
                    if (contentScope) {
                        contentScope.toggleFlyout();
                    }
                };
                this.getTogglerDimensions = function () {
                    return togglerScope.getTogglerDimensions();
                }
                this.setTogglerFocus = function () {
                    return togglerScope.setTogglerFocus();
                }

                this.closeFlyout = function (e) {
                    contentScope.closeFromChild(e);
                };
                this.gotFocus = function () {
                    contentScope.gotFocus();
                };

                this.updateAriaModel = function (val) {
                    scope.flyoutOpened = val;
                };

                var firstTabableElement = undefined,
                    lastTabableElement = undefined;

                var firstTabableElementKeyhandler = function (e) {
                    if (!e.keyCode) {
                        e.keyCode = e.which;
                    }
                    if (e.keyCode === keymap.KEY.TAB && e.shiftKey && scope.flyoutOpened) { 
                        contentScope.gotFocus();
                        events.preventDefault(e);
                        events.stopPropagation(e);
                    }
                };

                var lastTabableElementKeyhandler = function (e) {
                    if (!e.keyCode) {
                        e.keyCode = e.which;
                    }
                    if (e.keyCode === keymap.KEY.TAB && !e.shiftKey) {
                        contentScope.gotFocus();    
                        events.preventDefault(e);
                        events.stopPropagation(e);
                    }
                };
                this.associateTabEvent = function(){
                    $timeout(function () {
                        var element = elem[0].getElementsByClassName('b2b-flyout-container')[0];
                        firstTabableElement = b2bDOMHelper.firstTabableElement(element);
                        lastTabableElement = b2bDOMHelper.lastTabableElement(element);
                        if(angular.isUndefined(firstTabableElement)){
                            angular.element(element).css('display','block');
                            firstTabableElement = b2bDOMHelper.firstTabableElement(element);
                            lastTabableElement = b2bDOMHelper.lastTabableElement(element);
                            angular.element(element).css('display','none');
                        }
                        angular.element(firstTabableElement).bind('keydown', firstTabableElementKeyhandler);
                        angular.element(lastTabableElement).bind('keydown', lastTabableElementKeyhandler);
                    });
                }
                this.updateTabbableElements = function(){
                    $timeout(function () {
                        var element = elem[0].getElementsByClassName('b2b-flyout-container')[0];
                        angular.element(element).css('display','block');
                        firstTabableElement = b2bDOMHelper.firstTabableElement(element);
                        lastTabableElement = b2bDOMHelper.lastTabableElement(element);
                        angular.element(firstTabableElement).bind('keydown', firstTabableElementKeyhandler);
                        angular.element(lastTabableElement).bind('keydown', lastTabableElementKeyhandler);
                        angular.element(element).css('display','none');
                    });
                }
                this.unbindTabbaleEvents = function(){
                    if(angular.isDefined(firstTabableElement)){
                        angular.element(firstTabableElement).unbind('keydown', firstTabableElementKeyhandler);
                    }

                    if(angular.isDefined(lastTabableElement)){
                        angular.element(lastTabableElement).unbind('keydown', lastTabableElementKeyhandler);
                    }
                }
            }],
            link: function (scope, element, attrs, ctrl) {

            }
        };
    }])
    .directive('b2bFlyoutToggler', [function () {
        return {
            restrict: 'A',
            require: '^b2bFlyout',
            link: function (scope, element, attrs, ctrl) {
                element.bind('click', function (e) {
                    ctrl.toggleFlyoutState();
                });

                scope.getTogglerDimensions = function () {
                    return element[0].getBoundingClientRect();
                }

                scope.setTogglerFocus = function () {
                    element[0].focus();
                }

                ctrl.registerTogglerScope(scope);
            }
        };
    }])
    .directive('b2bFlyoutContent', ['$position', '$timeout', '$documentBind', '$isElement', '$document', function ($position, $timeout, $documentBind, $isElement, $document) {
        return {
            restrict: 'EA',
            transclude: true,
            replace: true,
            require: '^b2bFlyout',
            scope: {
                horizontalPlacement: '@',
                verticalPlacement: '@',
                flyoutStyle: '@',
                flyoutTitle: '@',
                contentUpdated: "=?"
            },
            templateUrl: 'b2bTemplate/flyout/flyoutContent.html',
            link: function (scope, element, attrs, ctrl) {
                var flyoutStyleArray, eachCssProperty, cssPropertyKey, cssPropertyVal, temp;
                scope.openFlyout = false;
                if (!scope.horizontalPlacement) {
                    scope.horizontalPlacement = 'center';
                }
                if (!scope.verticalPlacement) {
                    scope.verticalPlacement = 'below';
                }

                scope.toggleFlyout = function () {

                    scope.openFlyout = !scope.openFlyout;

                    if (scope.openFlyout) {

                        if (angular.isDefined(scope.flyoutStyle) && scope.flyoutStyle != "") {
                            flyoutStyleArray = scope.flyoutStyle.split(";");
                            for (i = 0; i < flyoutStyleArray.length; i++) {
                                eachCssProperty = flyoutStyleArray[i].split(":");
                                if (eachCssProperty.length == 2) {
                                    cssPropertyKey = eachCssProperty[0].trim();
                                    cssPropertyVal = eachCssProperty[1].trim();
                                    angular.element(element[0])[0].style[cssPropertyKey] = cssPropertyVal;
                                }
                            }
                        }

                        angular.element(element[0]).css({
                            'opacity': 0,
                            'display': 'block'
                        });

                        var flyoutIcons = angular.element(document.querySelectorAll(".b2b-flyout-icon"));
                        angular.forEach(flyoutIcons, function (elm) {
                            angular.element(elm)[0].blur();
                        });

                        $timeout(function () {
                            ctrl.setTogglerFocus();

                            var togglerDimensions = ctrl.getTogglerDimensions();
                            var flyoutDimensions = element[0].getBoundingClientRect();

                            switch (scope.horizontalPlacement) {
                            case "left":
                                angular.element(element[0]).css({
                                    'left': ((togglerDimensions.width / 2) - 26) + 'px'
                                });
                                break;
                            case "right":
                                angular.element(element[0]).css({
                                    'right': ((togglerDimensions.width / 2) - 23) + 'px'
                                });
                                break;  

                            case "centerLeft":
                                var marginLeft =  10-(flyoutDimensions.width)-20;
                                angular.element(element[0]).css({
                                    'margin-left': marginLeft + 'px'
                                });
                                break;
                            case "centerRight":
                                angular.element(element[0]).css({
                                    'left': ((togglerDimensions.width + 9 )) + 'px'
                                });
                                break;    

                            default:
                                var marginLeft = (togglerDimensions.width / 2) - (flyoutDimensions.width / 2) - 8;
                                angular.element(element[0]).css({
                                    'margin-left': marginLeft + 'px'
                                });
                            }

                            switch (scope.verticalPlacement) {
                            case "above":
                                angular.element(element[0]).css({
                                    'top': -(flyoutDimensions.height + 13) + 'px'
                                });
                                break;
                            case "centerLeft":
                                angular.element(element[0]).css({
                                    'top': -((togglerDimensions.height-13))+ 'px'
                                });
                                break;
                            case "centerRight":
                                angular.element(element[0]).css({
                                    'top': -(flyoutDimensions.height - 23)+ 'px'
                                });
                                break;                                    
                            default:
                                angular.element(element[0]).css({
                                    'top': (togglerDimensions.height + 13) + 'px'
                                });
                            }

                            angular.element(element[0]).css({
                                'opacity': 1
                            });
                        }, 100);
                    } else {
                        scope.hideFlyout();
                    }
                };

                scope.gotFocus = function () {
                    scope.openFlyout = false;
                    scope.hideFlyout();
                    ctrl.setTogglerFocus();
                    scope.$apply();
                };

                scope.closeFromChild = function (e) {
                    scope.openFlyout = false;
                    scope.hideFlyout();
                    ctrl.setTogglerFocus();
                    scope.$apply();
                };

                scope.hideFlyout = function () {
                    angular.element(element[0]).css({
                        'opacity': 0,
                        'display': 'none'
                    });
                };

                scope.closeFlyout = function (e) {
                    var isElement = $isElement(angular.element(e.target), element, $document);
                    if ((e.type === "keydown" && e.which === 27) || ((e.type === "click" || e.type==="touchend") && !isElement)) {
                        scope.openFlyout = false;
                        scope.hideFlyout();
                        ctrl.setTogglerFocus();
                        scope.$apply();
                    }
                };

                scope.$watch('openFlyout', function () {
                    ctrl.updateAriaModel(scope.openFlyout);
                });

                $documentBind.click('openFlyout', scope.closeFlyout, scope);
                $documentBind.event('keydown', 'openFlyout', scope.closeFlyout, scope);
                $documentBind.event('touchend', 'openFlyout', scope.closeFlyout, scope);
                ctrl.registerContentScope(scope);

                if (angular.isDefined(scope.contentUpdated) && scope.contentUpdated !== null) {
                    scope.$watch('contentUpdated', function (newVal, oldVal) {
                        if(newVal){
                            if (newVal !== oldVal) {
                                ctrl.unbindTabbaleEvents();
                                ctrl.associateTabEvent();
                            }
                            scope.contentUpdated = false;
                        } 
                    });
                }  

            }
        };
    }])
    .directive('b2bCloseFlyout', [function () {
        return {
            restrict: 'A',
            require: '^b2bFlyout',
            scope: {
                closeFlyout: '&'
            },
            link: function (scope, element, attrs, ctrl) {
                element.bind('click touchstart', function (e) {
                    scope.closeFlyout(e);
                    ctrl.closeFlyout(e);
                });
            }
        };
    }])
    .directive('b2bFlyoutTrapFocusInside', [function () {
        return {
            restrict: 'A',
            transclude: false,
            require: '^b2bFlyout',
            link: function (scope, elem, attr, ctrl) {
                /* Before opening modal, find the focused element */
                ctrl.updateTabbableElements();
            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Layouts.att:footer
 *
 * @description
 *  <file src="src/footer/docs/readme.md" />
 *
 * @usage
 * 
 <footer class="b2b-footer-wrapper" role="contentinfo" aria-label="footer">
        <div class="b2b-footer-container" b2b-column-switch-footer footer-link-items='footerItems'>
            <hr>
            <div class="divider-bottom-footer">
                <div class="span2 dispalyInline">&nbsp;</div>
                <div class="span6 dispalyInline">
                    <ul class="footer-nav-content">
                        <li><a href="Terms_of_use.html" title="Terms of use" id="foot0">Terms of use</a>|</li>
                        <li><a href="Privacy_policy.html" title="Privacy policy" id="foot1" class="active">Privacy policy</a>|</li>
                        <li><a href="Tollfree_directory_assistance.html" title="Tollfree directory assistance" id="foot2">Tollfree directory assistance</a>|</li>
                        <li><a href="compliance.html" title="Accessibility" id="foot3">Accessibility</a></li>

                    </ul>
                    <p><a href="//www.att.com/gen/privacy-policy?pid=2587" target="_blank">© <span class="copyright">2016</span> AT&amp;T Intellectual Property</a>. All rights reserved. AT&amp;T,the AT&amp;T Globe logo and all other AT&amp;T marks contained herein are tardemarks of AT&amp;T intellectual property and/or AT&amp;T affiliated companines.

                    </p>
                </div>
                <div class="span3 footerLogo dispalyInline">
                    <a href="index.html" class="footer-logo">
                        <i class="icon-att-globe"><span class="hidden-spoken">A T &amp; T</span></i>
                        <h2 class="logo-title">AT&amp;T</h2>
                    </a>
                </div>
            </div>

        </div>  
    </footer>

 * @example
 *  <section id="code">   
 <example module="b2b.att">
 <file src="src/footer/docs/demo.html" />
 <file src="src/footer/docs/demo.js" />
 </example>
 </section>
 *
 */
angular.module('b2b.att.footer', ['b2b.att.utilities']).
        directive('b2bColumnSwitchFooter', [function() {
                return {
                    restrict: 'A',
                    transclude: true,
                    scope: {
                        footerLinkItems: "="
                    },
                    templateUrl: 'b2bTemplate/footer/footer_column_switch_tpl.html',
                    link: function(scope) {
						var tempFooterColumns = scope.footerLinkItems.length;
						scope.footerColumns = 3;
                        if ( (tempFooterColumns === 5) || (tempFooterColumns === 4) ) {
							scope.footerColumns = tempFooterColumns;
                        }
                    }

                };

            }]);
     

/**
 * @ngdoc directive
 * @name Layouts.att:header
 *
 * @description
 *  <file src="src/header/docs/readme.md" />
 *
 * @usage
 *  <li b2b-header-menu class="header__item b2b-headermenu" ng-repeat="item in tabItems" role="presentation">
        <a href="#" class="menu__item" role="menuitem">{{item.title}}</a>
        <div class="header-secondary-wrapper">
            <ul class="header-secondary" role="menu">
                <li class="header-subitem" b2b-header-submenu ng-repeat="i in item.subitems" role="presentation">
                    <a href="#" class="menu__item" aria-haspopup="true" role="menuitem">{{i.value}}</a>
                    <div class="header-tertiary-wrapper" ng-if="i.links">
                        <ul class="header-tertiary" role="menu">
                            <li b2b-header-tertiarymenu ng-repeat="link in i.links" role="presentation">
                                <label>{{link.title}}</label>
                                <div b2b-tertiary-link ng-repeat="title in link.value">
                                    <a href="{{link.href}}" class="header-tertiaryitem" ng-if="!title.subitems" aria-haspopup="false" role="menuitem"><span class="b2b-label-hide">{{link.title}}</span>{{title.title}}</a>
                                    <a href="{{link.href}}" class="header-tertiaryitem" b2b-header-togglemenu ng-if="title.subitems" aria-haspopup="true" role="menuitem"><span class="b2b-label-hide">{{link.title}}</span>{{title.title}}</a>
                                    <ul class="header-quarternary" role="menu"  ng-if="title.subitems">
                                        <li b2b-header-quarternarymenu role="presentation">
                                            <a href="{{nav.href}}" ng-repeat="nav in title.subitems" role="menuitem" aria-haspopup="true">{{nav.title}}</a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </li> 
 *
 * @example
 *  <section id="code">
 <example module="b2b.att.header">
 <file src="src/header/docs/demo.html" />
 <file src="src/header/docs/demo.js" />
 </example>
 </section>
 *
 */
angular.module('b2b.att.header', ['b2b.att.dropdowns','b2b.att.utilities'])
	.directive('b2bHeaderMenu', ['keymap', '$documentBind', '$timeout', '$isElement', '$document', function (keymap, $documentBind, $timeout, $isElement, $document) {
        return {
            restrict: 'A',
            controller:['$scope',function($scope){
                this.nextSiblingFocus = function (elObj,flag) {
                        if (elObj.nextElementSibling) {
                            if(flag){
                                var nextmenuItem = this.getFirstElement(elObj.nextElementSibling,'a');
                                nextmenuItem.focus();
                            }else{
                                elObj.nextElementSibling.focus();
                            }
                        }
                };
                
                this.previousSiblingFocus = function (elObj,flag) {
                        if (elObj.previousElementSibling) {
                            if(flag){
                                var prevmenuItem = this.getFirstElement(elObj.previousElementSibling,'a');
                                prevmenuItem.focus();
                            }else{
                                elObj.previousElementSibling.focus();
                            }
                        }
                };
                    
                this.getFirstElement = function(elmObj,selector){
                        return elmObj.querySelector(selector);                        
                    };
            }],
            link: function (scope, elem,attr,ctrl) {
                scope.showMenu = false;
                var activeElm, subMenu, tertiaryMenu, el= angular.element(elem)[0], 
                        menuItem = angular.element(elem[0].children[0]);
                menuItem.bind('click', function () {
                    elem.parent().children().removeClass('active');
                    elem.addClass('active');
                    var elems= this.parentElement.parentElement.querySelectorAll('li[b2b-header-menu]>a');
                    for (var i=0; i<elems.length; i++) {
                        elems[i].setAttribute("aria-expanded",false);
                    }
                    scope.showMenu = true;
                    var elmTofocus = ctrl.getFirstElement(this.parentElement,'li[b2b-header-submenu]');
                    elmTofocus.firstElementChild.focus();
                    this.setAttribute('aria-expanded',true);
                    scope.$apply();
                });
               
                elem.bind('keydown', function (evt) {
                    activeElm = document.activeElement;
                    subMenu = ctrl.getFirstElement(activeElm.parentElement,'li[b2b-header-submenu]');
                    tertiaryMenu = ctrl.getFirstElement(activeElm.parentElement,'li[b2b-header-tertiarymenu]');
                    switch (evt.keyCode) {
                        case keymap.KEY.ENTER:
                        case keymap.KEY.SPACE:
                            elem[0].click();
                            break;
                        case keymap.KEY.UP:
                            evt.stopPropagation();
                            evt.preventDefault();
                            if (activeElm.parentElement.hasAttribute('b2b-header-submenu')) {
                                menuItem[0].focus();
                            }
                            break;
                        case keymap.KEY.DOWN:
                            evt.stopPropagation();
                            evt.preventDefault();
                            if (subMenu) {
                                subMenu.firstElementChild.focus();
                            } else if (tertiaryMenu) {
                                var firstSubitem = ctrl.getFirstElement(tertiaryMenu,'a.header-tertiaryitem');
                                firstSubitem.focus();
                            }
                            break;
                        case keymap.KEY.RIGHT:
                            evt.stopPropagation();
                            evt.preventDefault();
                            if (activeElm.parentElement.hasAttribute('b2b-header-submenu')) {
                                var elm = angular.element(activeElm.parentElement)[0];
                                ctrl.nextSiblingFocus(elm,true);
                            } else if (activeElm.parentElement.parentElement.hasAttribute('b2b-header-tertiarymenu')) {
                                var tertiaryLI = angular.element(activeElm.parentElement.parentElement)[0];
                                if (tertiaryLI.nextElementSibling) {
                                    var nextElm = ctrl.getFirstElement(tertiaryLI.nextElementSibling,"a.header-tertiaryitem");
                                    nextElm.focus();
                                }
                            }
                            else if(activeElm.parentElement.hasAttribute('b2b-header-menu')){
                                ctrl.nextSiblingFocus(el,true);
                            }
                            break;
                        case keymap.KEY.LEFT:
                            evt.stopPropagation();
                            evt.preventDefault();
                            if (activeElm.parentElement.hasAttribute('b2b-header-submenu')) {
                                var previousElm = angular.element(activeElm.parentElement)[0];
                                ctrl.previousSiblingFocus(previousElm,true);
                            } else if (activeElm.parentElement.parentElement.hasAttribute('b2b-header-tertiarymenu')) {
                                var tertiaryLI = angular.element(activeElm.parentElement.parentElement)[0];
                                if (tertiaryLI.previousElementSibling) {
                                    var prevElm = ctrl.getFirstElement(tertiaryLI.previousElementSibling,"a.header-tertiaryitem");
                                    prevElm.focus();
                                }
                            }
                            else if(activeElm.parentElement.hasAttribute('b2b-header-menu')) {
                                ctrl.previousSiblingFocus(el,true);
                            }
                            break;
                        case keymap.KEY.ESC:
                            evt.stopPropagation();
                            evt.preventDefault();
                            scope.showMenu = false;
                            elem.removeClass('active');
							menuItem.attr('aria-expanded',false);
							$timeout(function(){
								menuItem[0].focus();
							},100);
                            scope.$apply();
                            break;
                        default:
                            break;
                    }
                });
                var outsideClick = function (e) {
                    var isElement = $isElement(angular.element(e.target), elem, $document);
                    if (!isElement) {
                        scope.showMenu = false;
                        elem.removeClass('active');
                        scope.$apply();
                    }
                };
                $documentBind.click('showMenu', outsideClick, scope);
                $documentBind.touch('showMenu', outsideClick, scope);
            }
        };
    }]).directive('b2bHeaderSubmenu', ['$timeout',function ($timeout) {
        return{
            restrict: 'A',
            link: function (scope, elem) {
                var caretSign = angular.element("<i class='menuCaret'></i>");
                $timeout(function(){
					var menuItem = angular.element(elem[0].children[0]);
					menuItem.bind('focus mouseenter', function () {
						elem.parent().children().removeClass('active');
						elem.addClass('active');
						if(elem[0].childElementCount > 1){ // > 1 has third level menu
							menuItem.attr('aria-expanded',true);
							menuItem.attr('aria-haspopup',true);
						}
						var caretLeft = (elem[0].offsetLeft +  elem[0].offsetWidth/2) - 10;
						caretSign.css({left: caretLeft + 'px'});
						angular.element(caretSign);
						var tertiaryItems = elem[0].querySelectorAll('[b2b-header-tertiarymenu]');
						if(tertiaryItems.length >=1){
							elem.append(caretSign);
						}
					});
					menuItem.bind('blur', function () {
						$timeout(function () {
							var parentElm = document.activeElement.parentElement.parentElement;
							if(parentElm){
								if (!(parentElm.hasAttribute('b2b-header-tertiarymenu'))) {
									elem.removeClass('active');
									if(elem[0].childElementCount > 1){ // > 1 has third level menu
										menuItem.attr('aria-expanded',false);
									}
									var caret = elem[0].querySelector('.menuCaret');
									if(caret){
										caret.remove();
									}   
								}
							}
						});
					});
                });
            }
        };
    }]).directive('b2bHeaderTertiarymenu', ['$timeout','keymap', function ($timeout,keymap){
        return{
            restrict: 'A',
            require:'^b2bHeaderMenu',
            link: function (scope, elem,attr,ctrl) {
                
                elem.bind('keydown', function (evt) {
                    var activeElm = document.activeElement;
                    var activeParentElm = activeElm.parentElement;
                    var activeParentObj  = angular.element(activeParentElm)[0];
                    
                    if(activeParentElm.hasAttribute('b2b-tertiary-link')){
                        var quarterNav = angular.element(activeParentElm)[0].querySelector('li[b2b-header-quarternarymenu]');
                        if(quarterNav){
                            var links = ctrl.getFirstElement(angular.element(quarterNav)[0],'a');
                        }
                    }
                    var tertiaryMenu = activeElm.parentElement.parentElement.parentElement;
                    var tertiaryMenuFlag = tertiaryMenu.hasAttribute('b2b-tertiary-link');
                    
                    switch (evt.keyCode) {
                        case keymap.KEY.DOWN:
                            evt.stopPropagation();
                            evt.preventDefault();
                            if (activeParentElm.hasAttribute('b2b-tertiary-link')) {
                                if(angular.element(quarterNav).hasClass('active')){
                                    links.focus();
                                }else if(activeParentObj.nextElementSibling){
                                    ctrl.nextSiblingFocus(activeParentObj,true);
                                }
                            }
                            else if(angular.element(activeParentElm).hasClass('active')){
                                ctrl.nextSiblingFocus(activeElm);
                            }
                            break;                        
                        case keymap.KEY.UP:
                            evt.stopPropagation();
                            evt.preventDefault();
                            if(activeParentElm.hasAttribute('b2b-tertiary-link')){
                                if(activeParentObj.previousElementSibling.hasAttribute('b2b-tertiary-link')){
                                    ctrl.previousSiblingFocus(activeParentObj,true);
                                }else{
                                    var elm = angular.element(activeElm.parentElement.parentElement.parentElement.parentElement.parentElement)[0];
                                    ctrl.getFirstElement(elm,"a").focus();
                                }
                            }else if(angular.element(activeParentElm).hasClass('active')){
                                    if (activeElm.previousElementSibling) {
                                        ctrl.previousSiblingFocus(activeElm);
                                    }else if (tertiaryMenuFlag) {
                                        var elm = angular.element(tertiaryMenu)[0];
                                        ctrl.getFirstElement(elm,"a.header-tertiaryitem").focus();
                                    }
                                }
                            break;
                        default:
                            break;
                    }
                });
            }            
        };          
    }]).directive('b2bHeaderTogglemenu', ['$timeout', 'keymap', function ($timeout, keymap) {
        return{
            restrict: 'A',
            require: '^b2bHeaderMenu',
            link: function (scope, elem, attrs, ctrl) {
                var quarterNav;
                $timeout(function () {
                    quarterNav = angular.element(elem.parent())[0].querySelector('li[b2b-header-quarternarymenu]');
                    elem.bind('click', function () {
                        angular.element(quarterNav).toggleClass('active');
                    });
                });
            }
        };
    }]).directive('b2bHeaderResponsive', ['$timeout',function ($timeout) {
        return{
            restrict: 'A',
			controller: function($scope){
				this.applyMediaQueries = function(value){
					document.querySelector('style').textContent += 
						"@media screen and (max-width:950px) { \
							.header__item.profile { right: " + value + "px; } \
						}";
				};
				this.arrangeResponsiveHeader = function(children){
					/* 
					 * clientWidth of 1090 === max-width of 1100px
					 * clientWidth of 920 === max-width of 950px
					 * see b2b-angular.css for rest of responsive header CSS
					 */
				  if (document.documentElement.clientWidth <= 920) { 
						switch(children){
							case 1:
								this.applyMediaQueries(200);					
								break;
							case 2:
								this.applyMediaQueries(200);							
								break;
							default: // anthing above 3, however, should not have more than 3 to date
								this.applyMediaQueries(200);																			
						}
					}
				}
			},
            link: function (scope, elem, attrs, ctrl) {
				var children;
				var profile;
				
				// onload of page
				$timeout(function(){ 
					profile = document.querySelector('li.header__item.profile');
					children = angular.element(profile).children().length;
					
					ctrl.arrangeResponsiveHeader(children); // shift right-side icon flyovers
				});

				// on screen resize
				window.addEventListener('resize', function(event){ // caret adjustmet
					var activeSubmenu = elem[0].querySelector('[b2b-header-menu] [b2b-header-submenu].active');
					var activeSubmenuEl = angular.element(activeSubmenu);
					if(activeSubmenu){
						var caretSign = activeSubmenu.querySelector('i.menuCaret');
						if(caretSign){
							var caretSignEl = angular.element(caretSign);
							var caretLeft = (activeSubmenu.offsetLeft +  activeSubmenu.offsetWidth/2) - 10;
							caretSignEl.css({left: caretLeft + 'px'});
						}
					}

					ctrl.arrangeResponsiveHeader(children); // shift right-side icon flyovers
				});
            }
        };
	}]);

/**
 * @ngdoc directive
 * @name Layouts.att:headings & copy
 *
 * @description
 *  <file src="src/headingsAndCopy/docs/readme.md" />
 *
 * @example
 <section id="code">
    <b>HTML + AngularJS</b>
    <example module="b2b.att">
    <file src="src/headingsAndCopy/docs/demo.html" />
</example>
</section>
 */

var b2bLegalCopy = angular.module('b2b.att.headingsAndCopy', []);
/**
 * @ngdoc directive
 * @name Tabs, tables & accordions.att:horizontalTable
 *
 * @description
 *  <file src="src/horizontalTable/docs/readme.md" />
 *
 * @usage
 * @param {int} sticky - Number of sticky columns to have. Maximum of 3.
 * @param {boolean} refresh - A boolean that when set to true will force a re-render of table. Only use when using 'bulk mode'
 * @param {string} legendContent - A string of html to fill in the legend flyout. This should generally be a <ul> with <li> and should not rely on Angular for repeating.
 * @param {boolean} retainColumnSet - A boolean that on re-render of the table, determines if the columns visible should reset to 0 or not. Default is false. 
 * @param {boolean} columnsUpdated - A boolean that needs to be set to when the number of columns are increased or decreased, or if the ordering of the columns is changed.
 * @param {array} columnsPerView - A list of integers that tells how many columns needs to be displayed per view.
 * @param {int} defaultNumberOfColumns - An integer that tells how many columns needs to be displayed per each view. By default value is 6. This can be used if the number of columns displayed per each view is constant instaed of columnsPerView attribue. 
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/horizontalTable/docs/demo.html" />
            <file src="src/horizontalTable/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.horizontalTable', [])
    .constant('b2bHorizontalTableConfig', {
        'maxStickyColumns': 3,
        'defaultNumberOfColumns': 6
    })
    .directive('b2bHorizontalTable', ['$timeout', 'b2bHorizontalTableConfig', function ($timeout, b2bHorizontalTableConfig) {
        return {
            restrict: 'EA',
            scope: true,
            transclude: true,
            scope: {
                numOfStickyCols: '=?sticky',
                refresh: '=?',
                legendContent: '=?',
                retainColumnSet: '=?',
                columnsUpdated: '=?',
                columnsPerView: '=?',
                defaultNumberOfColumns: '=?'
            },
            templateUrl: 'b2bTemplate/horizontalTable/horizontalTable.html',
            controller: ['$scope', '$element', '$attrs', function (scope, elem, attr) {
                var columnSets = [];
                var currentSet = [];
                var setIndex = -1;
                var thElements = elem.find('th');
                var tableColumns = [];
                var tableRows = elem.find('tr');
                var displayNoneCSS = {'display': 'none'};
                var displayBlockCSS = {'display': 'table-cell'};
                var endIndex = -1;
                var startDisplayIndex = -1;
                var endDisplayIndex = -1;

                if (!(attr.retainColumnSet !== undefined && attr.retainColumnSet !== ''))  {
                    scope.retainColumnSet = true;
                }
                var defaultNumberOfColumns = attr.defaultNumberOfColumns ? scope.$eval(attr.defaultNumberOfColumns) : b2bHorizontalTableConfig.defaultNumberOfColumns;
                function init(){
                    defaultNumberOfColumns = attr.defaultNumberOfColumns ? scope.$eval(attr.defaultNumberOfColumns) : b2bHorizontalTableConfig.defaultNumberOfColumns;
                    tableColumns = [];
                    scope.countDisplayText = "";
                    for(var count = 1; count <= scope.numOfStickyCols; count++) {
                        scope.countDisplayText = scope.countDisplayText + count + ", "
                    }
                    tableRows = elem.find('tr');
                    angular.forEach(tableRows, function(row, rowIndex) {
                        for(var j = 0; j < row.children.length; j++) {
                            if (tableColumns[j] === undefined) {
                                tableColumns[j] = [];
                            }
                            tableColumns[j].push(row.children[j]);
                        }
                    });
                    scope.numOfCols = tableColumns.length;
                    columnSets = [];
                    var tempIndex = 0;
                    for (var i = scope.numOfStickyCols; i < tableColumns.length;) {
                        if(attr.columnsPerView !== undefined && attr.columnsPerView !== ''){
                            endIndex = i+scope.columnsPerView[tempIndex]-scope.numOfStickyCols-1;
                            tempIndex++;
                        }else{
                            endIndex = i+defaultNumberOfColumns-scope.numOfStickyCols-1;
                        }
                        
                        if(endIndex > tableColumns.length-1){
                            endIndex = tableColumns.length-1
                        }
                        columnSets.push([i, endIndex]);
                        i = endIndex + 1;
                    }
                    
                    for(var i = 0; i < scope.numOfStickyCols; i++) {
                        for (var j = 0; j < tableRows.length; j++) {
                            thObject = angular.element(tableRows[j].children[i]);
                            angular.element(thObject).css({
                                'background-color': '#F2F2F2'
                            });
                        }
                    }
                    thElements = elem.find('th');
                    currentSet = columnSets[setIndex];
                    checkScrollArrows();
                    if(!scope.retainColumnSet){
                        setIndex = 0;
                        for(var i = 0; i < thElements.length; i++){
                            angular.element(thElements[i]).css(displayNoneCSS)
                        }
                        for(var i = 0; i < defaultNumberOfColumns; i++){
                            angular.element(thElements[i]).css(displayBlockCSS)
                        }

                        if (!scope.$$phase) {
                            scope.$apply();
                        }
                    }
                        if (!scope.$$phase) {
                            scope.$apply();
                        }
                }

                $timeout(function () {
                    setIndex = 0;
                    init();
                },200);

                if (scope.refresh !== undefined) {
                    scope.$watch('refresh', function(oldVal, newVal) {
                        if (scope.refresh) { 
                            // From testing it takes about 30 ms before ngRepeat executes, so let's set initial timeout
                            // NOTE: May need to expose timeout to developers. Application is known to have digest cycle of 3-5k watches.
                            $timeout(init, 100, false);
                            scope.refresh = false;
                        }
                    });
                }
                if (scope.columnsUpdated !== undefined) {
                    scope.$watch('columnsUpdated', function(oldVal, newVal) {
                        if (scope.columnsUpdated) { 
                            // From testing it takes about 30 ms before ngRepeat executes, so let's set initial timeout
                            // NOTE: May need to expose timeout to developers. Application is known to have digest cycle of 3-5k watches.
                            setIndex = 0;
                            $timeout(function(){
                                init();
                                updateTableCellDisplay(columnSets[setIndex]);
                            },100);
                            scope.columnsUpdated = false;                            
                        }
                    });
                }
                

                scope.getColumnSet = function () {
                    return columnSets[setIndex];
                };

                this.updateCellDisplay = function(columnIndex,columnElement){
                    if(setIndex === -1 || !scope.retainColumnSet || currentSet.length === 0){
                        startDisplayIndex = 0;
                        endDisplayIndex = defaultNumberOfColumns - 1;
                    }else{
                        startDisplayIndex = currentSet[0];
                        endDisplayIndex = currentSet[1];
                    }
                    if((columnIndex >= startDisplayIndex && columnIndex <= endDisplayIndex) || (columnIndex < scope.numOfStickyCols)){
                        angular.element(columnElement).css(displayBlockCSS);
                    }else{
                        angular.element(columnElement).css(displayNoneCSS);
                    }
                };

                function updateTableCellDisplay(set) {
                    currentSet = set;
                    for (var i = scope.numOfStickyCols; i < tableColumns.length; i++) {
                        angular.element(tableColumns[i]).css(displayNoneCSS);
                    }

                    for (var i = set[0]; i <= set[1]; i++) {
                        angular.element(tableColumns[i]).css(displayBlockCSS);
                    }
                }

                function checkScrollArrows() {
                    scope.disableLeft = (setIndex === 0);
                    scope.disableRight = !(setIndex < columnSets.length-1);
                }

                scope.moveViewportLeft = function () {
                    setIndex--;
                    updateTableCellDisplay(columnSets[setIndex]);
                    checkScrollArrows();
                    if (scope.disableLeft) {
                        elem[0].querySelector('.b2b-horizontal-table-column-info').focus();
                    }
                };
                
                scope.moveViewportRight = function () {
                    setIndex++;
                    updateTableCellDisplay(columnSets[setIndex]);
                    checkScrollArrows();
                    if (scope.disableRight) {
                        elem[0].querySelector('.b2b-horizontal-table-column-info').focus();
                    }
                };
            }],
            link: function (scope, element, attrs, ctrl) {
                
            }
        };
    }])
    .directive('b2bTableColumnToggler', [function () {
        return {
            restrict: 'A',
            require: '^b2bHorizontalTable',
            link: function (scope, element, attrs, ctrl) {
                ctrl.updateCellDisplay(scope.$eval(attrs.b2bTableColumnToggler),element);
            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Forms.att:hourPicker
 *
 * @description
 *  <file src="src/hourPicker/docs/readme.md" />
 *
 * @usage
 * <div b2b-hourpicker ng-model="hourpickerValue.value"></div>
    
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/hourPicker/docs/demo.html" />
            <file src="src/hourPicker/docs/demo.js" />
        </example>
    </section>
 *
 */
angular.module('b2b.att.hourPicker', ['b2b.att.utilities'])

.constant('b2bHourpickerConfig', {
    dayOptions: [{
        title: 'sunday',
        caption: 'Sun',
        label: 'S',
        disabled: false
    }, {
        title: 'monday',
        caption: 'Mon',
        label: 'M',
        disabled: false
    }, {
        title: 'tuesday',
        caption: 'Tues',
        label: 'T',
        disabled: false
    }, {
        title: 'wednesday',
        caption: 'Wed',
        label: 'W',
        disabled: false
    }, {
        title: 'thursday',
        caption: 'Thu',
        label: 'T',
        disabled: false
    }, {
        title: 'friday',
        caption: 'Fri',
        label: 'F',
        disabled: false
    }, {
        title: 'saturday',
        caption: 'Sat',
        label: 'S',
        disabled: false
    }],
    startTimeOptions: ['1:00', '2:00', '3:00', '4:00', '5:00', '6:00', '7:00', '8:00', '9:00', '10:00', '11:00', '12:00'],
    startTimeDefaultOptionIndex: -1,
    startTimeDefaultMeridiem: "am",
    endTimeOptions: ['1:00', '2:00', '3:00', '4:00', '5:00', '6:00', '7:00', '8:00', '9:00', '10:00', '11:00', '12:00'],
    endTimeDefaultOptionIndex: -1,
    endTimeDefaultMeridiem: "pm",
    sameDayOption: true
})

.factory('b2bNormalizeHourpickerValues', [function () {
    var _normalize = function (hourpickerValues) {
        if (angular.isDefined(hourpickerValues) && hourpickerValues != null) {
            var finalHourpickerValues = [];
            var hourpickerValue = {};
            var days = {};
            for (var i = 0; i < hourpickerValues.length; i++) {
                days = hourpickerValues[i].days ? hourpickerValues[i].days : {};
                hourpickerValue.startTime = hourpickerValues[i].startTime ? hourpickerValues[i].startTime : '';
                hourpickerValue.startMeridiem = hourpickerValues[i].startMeridiem ? hourpickerValues[i].startMeridiem : '';
                hourpickerValue.endTime = hourpickerValues[i].endTime ? hourpickerValues[i].endTime : '';
                hourpickerValue.endMeridiem = hourpickerValues[i].endMeridiem ? hourpickerValues[i].endMeridiem : '';
                hourpickerValue.days = [];

                var retrieveDaysText = function (daysDetails) {
                    var daysTexts = [];
                    var first = -1;
                    var last = -1;
                    var index = -1;
                    for (var i in days) {
                        if (days[i].value) {
                            daysTexts.push(i);
                        }
                    }

                    first = daysTexts[0];
                    last = daysTexts[0];
                    index = 0;
                    hourpickerValue.days[index] = days[first].caption;
                    if (daysTexts.length > 1) {
                        for (var i = 1; i < daysTexts.length; i++) {
                            if (daysTexts[i] - last === 1) {
                                last = daysTexts[i];
                                hourpickerValue.days[index] = days[first].caption + ' - ' + days[last].caption;
                            } else {
                                index++;
                                first = last = daysTexts[i];
                                hourpickerValue.days[index] = days[first].caption;
                            }
                        }
                    }
                };
                retrieveDaysText();

                finalHourpickerValues.push(angular.copy(hourpickerValue));
            }

            return angular.copy(finalHourpickerValues);
        }
    };

    return {
        normalize: _normalize
    };
}])

.directive('b2bHourpicker', ['b2bHourpickerConfig', 'b2bNormalizeHourpickerValues', function (b2bHourpickerConfig, b2bNormalizeHourpickerValues) {
    return {
        restrict: 'EA',
        replace: false,
        scope: true,
        require: 'ngModel',
        templateUrl: 'b2bTemplate/hourPicker/b2bHourpicker.html',
        controller: ['$scope', function (scope) {

        }],
        link: function (scope, elem, attr, ctrl) {
            scope.hourpicker = {};
            scope.hourpicker.dayOptions = attr.dayOptions ? scope.$parent.$eval(attr.dayOptions) : b2bHourpickerConfig.dayOptions;
            scope.hourpicker.startTimeOptions = attr.startTimeOptions ? scope.$parent.$eval(attr.startTimeOptions) : b2bHourpickerConfig.startTimeOptions;
            scope.hourpicker.endTimeOptions = attr.endTimeOptions ? scope.$parent.$eval(attr.endTimeOptions) : b2bHourpickerConfig.endTimeOptions;
            scope.hourpicker.startTimeDefaultOptionIndex = attr.startTimeDefaultOptionIndex ? scope.$parent.$eval(attr.startTimeDefaultOptionIndex) : b2bHourpickerConfig.startTimeDefaultOptionIndex;
            scope.hourpicker.endTimeDefaultOptionIndex = attr.endTimeDefaultOptionIndex ? scope.$parent.$eval(attr.endTimeDefaultOptionIndex) : b2bHourpickerConfig.endTimeDefaultOptionIndex;
            scope.hourpicker.startTimeDefaultMeridiem = attr.startTimeDefaultMeridiem ? scope.$parent.$eval(attr.startTimeDefaultMeridiem) : b2bHourpickerConfig.startTimeDefaultMeridiem;
            scope.hourpicker.endTimeDefaultMeridiem = attr.endTimeDefaultMeridiem ? scope.$parent.$eval(attr.endTimeDefaultMeridiem) : b2bHourpickerConfig.endTimeDefaultMeridiem;
            scope.hourpicker.sameDayOption = attr.sameDayOption ? scope.$parent.$eval(attr.sameDayOption) : b2bHourpickerConfig.sameDayOption;
            scope.hourpicker.editMode = -1;

            scope.hourpickerValues = [];
            scope.finalHourpickerValues = [];
            scope.addHourpickerValue = function (hourpickerPanelValue) {
                if (hourpickerPanelValue) {
                    if (scope.hourpicker.editMode > -1) {
                        scope.hourpickerValues[scope.hourpicker.editMode] = hourpickerPanelValue;
                        scope.hourpicker.editMode = -1;
                    } else {
                        scope.hourpickerValues.push(hourpickerPanelValue);
                    }
                }
                scope.finalHourpickerValues = b2bNormalizeHourpickerValues.normalize(angular.copy(scope.hourpickerValues));
                ctrl.$setViewValue(angular.copy(scope.hourpickerValues));
            };
            ctrl.$render = function () {
                if (angular.isDefined(ctrl.$modelValue)) {
                    scope.hourpickerValues = angular.copy(ctrl.$modelValue);
                    scope.finalHourpickerValues = b2bNormalizeHourpickerValues.normalize(angular.copy(scope.hourpickerValues));
                }
            };
            scope.editHourpickerValue = function (index) {
                scope.hourpickerPanelValue = angular.copy(scope.hourpickerValues[index]);
                scope.hourpicker.editMode = index;
            };
            scope.deleteHourpickerValue = function (index) {
                scope.hourpickerValues.splice(index, 1);
                scope.resetHourpickerPanelValue();
                scope.addHourpickerValue();
            };

            scope.setValidity = function (errorType, errorValue) {
                ctrl.$setValidity(errorType, errorValue);
            }
        }
    }
}])

.directive('b2bHourpickerPanel', [function () {
    return {
        restrict: 'EA',
        replace: false,
        templateUrl: 'b2bTemplate/hourPicker/b2bHourpickerPanel.html',
        controller: ['$scope', function (scope) {

        }],
        link: function (scope, elem, attr, ctrl) {
            var hourpickerPanelValueTemplate = {
                days: {},
                startTime: '',
                startMeridiem: 'am',
                endTime: '',
                endMeridiem: 'pm'
            };
            for (var i = 0; i < scope.hourpicker.dayOptions.length; i++) {
                hourpickerPanelValueTemplate.days[i] = {
                    value: false,
                    title: scope.hourpicker.dayOptions[i].title,
                    caption: scope.hourpicker.dayOptions[i].caption
                };
            }
            scope.hourpickerPanelValue = {};
            scope.disableAddBtn = true;

            scope.$watch('hourpickerPanelValue.days', function(){
                for(var i in scope.hourpickerPanelValue.days)
                {
                    if(scope.hourpickerPanelValue.days[i].value)
                    {
                        scope.disableAddBtn = false;
                        break;
                    }
                    scope.disableAddBtn = true;
                }
            }, true);

            scope.resetHourpickerPanelValue = function () {
                scope.hourpickerPanelValue = angular.copy(hourpickerPanelValueTemplate);
                if (scope.hourpicker.startTimeDefaultOptionIndex > -1) {
                    scope.hourpickerPanelValue.startTime = scope.hourpicker.startTimeOptions[scope.hourpicker.startTimeDefaultOptionIndex];
                }
                if (scope.hourpicker.endTimeDefaultOptionIndex > -1) {
                    scope.hourpickerPanelValue.endTime = scope.hourpicker.endTimeOptions[scope.hourpicker.endTimeDefaultOptionIndex];
                }
                scope.hourpickerPanelValue.startMeridiem = scope.hourpicker.startTimeDefaultMeridiem;
                scope.hourpickerPanelValue.endMeridiem = scope.hourpicker.endTimeDefaultMeridiem;
                scope.hourpicker.editMode = -1;
                scope.setValidity('invalidHourpickerData', true);
                scope.setValidity('invalidHourpickerTimeRange', true);
            };
            scope.resetHourpickerPanelValue();
            scope.updateHourpickerValue = function () {
                if (scope.isFormValid() && !scope.isTimeOverlap()) {
                    scope.addHourpickerValue(angular.copy(scope.hourpickerPanelValue));
                    scope.resetHourpickerPanelValue();
                }
            };

            scope.isFormValid = function () {
                var isStartTimeAvailable = scope.hourpickerPanelValue.startTime ? true : false;
                var isStartMeridiemAvailable = scope.hourpickerPanelValue.startMeridiem ? true : false;
                var isEndTimeAvailable = scope.hourpickerPanelValue.endTime ? true : false;
                var isEndMeridiemAvailable = scope.hourpickerPanelValue.endMeridiem ? true : false;
                var currentStartTime = getTime(scope.hourpickerPanelValue.startTime, scope.hourpickerPanelValue.startMeridiem);
                var currentEndTime = getTime(scope.hourpickerPanelValue.endTime, scope.hourpickerPanelValue.endMeridiem);
                var isTimeInProperSequence = currentEndTime > currentStartTime;
                var isDayChecked = false;
                for (var i in scope.hourpickerPanelValue.days) {
                    if (scope.hourpickerPanelValue.days[i].value) {
                        isDayChecked = true;
                        break;
                    }
                }

                if (isStartTimeAvailable && isStartMeridiemAvailable && isEndTimeAvailable && isEndMeridiemAvailable && isTimeInProperSequence && isDayChecked) {
                    scope.setValidity('invalidHourpickerData', true);
                    return true;
                } else {
                    scope.setValidity('invalidHourpickerData', false);
                    return false;
                }
            };
            scope.isTimeOverlap = function () {
                var selectedDays = [];
                for (var i in scope.hourpickerPanelValue.days) {
                    if (scope.hourpickerPanelValue.days[i].value) {
                        selectedDays.push(i);
                    }
                }

                var currentStartTime, currentEndTime, existingStartTime, existingEndTime;
                currentStartTime = getTime(scope.hourpickerPanelValue.startTime, scope.hourpickerPanelValue.startMeridiem);
                currentEndTime = getTime(scope.hourpickerPanelValue.endTime, scope.hourpickerPanelValue.endMeridiem);
                for (var i = 0; i < scope.hourpickerValues.length; i++) {
                    
                    if (i === scope.hourpicker.editMode) {
                        continue;
                    }

                    for (var j = 0; j < selectedDays.length; j++) {
                        existingStartTime = getTime(scope.hourpickerValues[i].startTime, scope.hourpickerValues[i].startMeridiem);
                        existingEndTime = getTime(scope.hourpickerValues[i].endTime, scope.hourpickerValues[i].endMeridiem);
                        if (scope.hourpickerValues[i].days[selectedDays[j]].value) {
                            if(!scope.hourpicker.sameDayOption){
                                scope.setValidity('dayAlreadySelected', false);
                                return true;
                            } else if ((currentStartTime > existingStartTime && currentStartTime < existingEndTime) || (currentEndTime > existingStartTime && currentEndTime < existingEndTime)) {
                                scope.setValidity('invalidHourpickerTimeRange', false);
                                return true;
                            } else if ((existingStartTime > currentStartTime && existingStartTime < currentEndTime) || (existingEndTime > currentStartTime && existingEndTime < currentEndTime)) {
                                scope.setValidity('invalidHourpickerTimeRange', false);
                                return true;
                            } else if ((currentStartTime === existingStartTime) && (currentEndTime === existingEndTime)) {
                                scope.setValidity('invalidHourpickerTimeRange', false);
                                return true;
                            }
                        }
                    }
                }

                scope.setValidity('dayAlreadySelected', true);
                scope.setValidity('invalidHourpickerTimeRange', true);
                return false;
            };
            var getTime = function (timeString, meridiem) {
                var tempDate = new Date();
                if (timeString && meridiem) {
                    var timeSplit = timeString.split(':');
                    var hour = ((meridiem === 'PM' || meridiem === 'pm') && timeSplit[0] !== '12') ? parseInt(timeSplit[0], 10) + 12 : parseInt(timeSplit[0], 10);
                    tempDate.setHours(hour, parseInt(timeSplit[1], 10), 0, 0);
                }

                return tempDate.getTime();
            };
        }
    }
}])

.directive('b2bHourpickerValue', [function () {
    return {
        restrict: 'EA',
        replace: false,
        templateUrl: 'b2bTemplate/hourPicker/b2bHourpickerValue.html',
        controller: ['$scope', function (scope) {

        }],
        link: function (scope, elem, attr, ctrl) {
            scope.hourpickerValue = {};
            scope.hourpickerValue.startTime = attr.startTime ? scope.$eval(attr.startTime) : '';
            scope.hourpickerValue.startMeridiem = attr.startMeridiem ? scope.$eval(attr.startMeridiem) : '';
            scope.hourpickerValue.endTime = attr.endTime ? scope.$eval(attr.endTime) : '';
            scope.hourpickerValue.endMeridiem = attr.endMeridiem ? scope.$eval(attr.endMeridiem) : '';
            scope.hourpickerValue.days = attr.days ? scope.$eval(attr.days).join(', ') : '';
            scope.hourpickerValue.index = attr.b2bHourpickerValue ? scope.$eval(attr.b2bHourpickerValue) : -1;
        }
    }
}]);
/**
 * @ngdoc directive
 * @name Template.att:inputTemplate
 *
 * @description
 *  <file src="src/inputTemplate/docs/readme.md" />
 *
 * @usage
 *  <input type="text" id="fieldId" placeholder="placholder text here" class="span12 input-enhanced" name="fieldName">
 *
 * @example
 <section id="code">
    <b>HTML + AngularJS</b>
    <example module="b2b.att">
        <file src="src/inputTemplate/docs/demo.html" />
        <file src="src/inputTemplate/docs/demo.js" />
    </example>
 </section>
 */
angular.module('b2b.att.inputTemplate', []);

/**
 * @ngdoc directive
 * @name Navigation.att:leftNavigation
 *
 * @description
 *  <file src="src/leftNavigation/docs/readme.md" />
 *
 * @usage
 *   <b2b-left-navigation data-menu="menuData"></b2b-left-navigation> 
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/leftNavigation/docs/demo.html" />
            <file src="src/leftNavigation/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.leftNavigation', [])
    .directive('b2bLeftNavigation', [function () {
        return {
            restrict: 'EA',
            templateUrl: 'b2bTemplate/leftNavigation/leftNavigation.html',
            scope: {
                menuData: '='
            },
            link: function (scope, element, attrs, ctrl) {
                scope.idx = -1;
                scope.itemIdx = -1;
                scope.navIdx = -1;
                scope.toggleNav = function (val) {
                    if (val === scope.idx) {
                        scope.idx = -1;
                        return;
                    }
                    scope.idx = val;
                };
                scope.liveLink = function (evt, val1, val2) {
                    scope.itemIdx = val1;
                    scope.navIdx = val2;
                    evt.stopPropagation();
                };
            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Buttons, links & UI controls.att:links
 *
 * @description
 *  <file src="src/links/docs/readme.md" />
 * @usage
 *      <!-- See below examples for link implementation -->
 *      
 * @example
       <section id="code">              
           <b>HTML + AngularJS</b>
           <example module="b2b.att">
           <file src="src/links/docs/demo.html" />
            <file src="src/links/docs/demo.js" />            
          </example>          
        </section>
 */
angular.module('b2b.att.links', []);
/**
 * @ngdoc directive
 * @name Misc.att:listbox
 *
 * @description
 *  <file src="src/listbox/docs/readme.md" />
 *
 * @param {int} currentIndex - Current index of selected listbox item. Is not supported on multiselect listbox
 * @param {Array} listboxData - Data of listbox items. Should include full data regardless if HTML will be filtered.

 * @example
 *  <section id="code">   
     <example module="b2b.att">
     <file src="src/listbox/docs/demo.html" />
     <file src="src/listbox/docs/demo.js" />
     </example>
    </section>
 *
 */
angular.module('b2b.att.listbox', ['b2b.att.utilities'])
.directive('b2bListBox', ['keymap', 'b2bDOMHelper', '$rootScope', function(keymap, b2bDOMHelper, $rootScope) {
                return {
                    restrict: 'AE',
                    transclude: true,
                    replace: true,
                    scope: {
                        currentIndex: '=', 
                        listboxData: '='
                    },
                    templateUrl: 'b2bTemplate/listbox/listbox.html',
                    link: function(scope, elem, attr) {

                        if (attr.ariaMultiselectable !== undefined || attr.ariaMultiselectable === 'true') {
                            scope.multiselectable = true;
                        } else {
                            scope.multiselectable = false;
                        }

                        var shiftKey = false;
                        var elements = [];
                        var prevDirection = undefined; // previous direction is used for an edge case when shifting
                        var shiftKeyPressed = false; // Used to handle shift clicking
                        var ctrlKeyPressed = false;

                        var currentIndexSet = {
                            'elementIndex': 0,
                            'listboxDataIndex': 0
                        };

                        function isTrue(item) {
                            if (item.selected === true) {
                                return true;
                            }
                        }

                        function incrementIndex(elem) {
                            $rootScope.$apply();

                            var nextElem = elem.next();
                            if (!angular.isDefined(nextElem) || nextElem.length === 0) {
                                return;
                            }

                            currentIndexSet.elementIndex += 1;
                            currentIndexSet.listboxDataIndex = parseInt(nextElem.attr('data-index'), 10);
                            scope.currentIndex = currentIndexSet.listboxDataIndex;

                            if (currentIndexSet.elementIndex >= elements.length - 1) {
                                currentIndexSet.elementIndex = elements.length-1;
                            }
                        }

                        function decrementIndex(elem) {
                            $rootScope.$apply();
                            var prevElem = angular.element(b2bDOMHelper.previousElement(elem));
                            if (!angular.isDefined(prevElem) || prevElem.length === 0) {
                                return;
                            }

                            currentIndexSet.elementIndex -= 1;
                            currentIndexSet.listboxDataIndex = parseInt(prevElem.attr('data-index'), 10);
                            scope.currentIndex = currentIndexSet.listboxDataIndex;

                            if (currentIndexSet.elementIndex <= 0) {
                                currentIndexSet.elementIndex = 0;
                            }
                        }

                        var focusOnElement = function(index) {
                            try {
                                elements[index].focus();
                            } catch (e) {};
                        }

                        function selectItems(startIndex, endIndex, forceValue) {
                            for (var i = startIndex; i < endIndex; i++) {
                                if (forceValue === undefined) {
                                    // We will flip the value
                                    scope.listboxData[i].selected = !scope.listboxData[i].selected;
                                } else {
                                    scope.listboxData[i].selected = forceValue;
                                }
                            }

                            if (!scope.$$phase) {
                                scope.$apply();
                            }
                        }

                        elem.bind('focus', function(evt) { 
                            // If multiselectable or not and nothing is selected, put focus on first element 
                            // If multiselectable and a range is set, put focus on first element of range 
                            // If not multiselectable and something selected, put focus on element 
                            elements = elem.children(); 
                             var selectedItems = scope.listboxData.filter(isTrue); 
                             var elementsIndies = Array.prototype.map.call(elements, function(item) {
                                return parseInt(angular.element(item).attr('data-index'), 10);
                            });
 
                            if (selectedItems.length == 0) { 
                                focusOnElement(0); 
                                currentIndexSet.listboxDataIndex = 0;
                            } else if (attr.ariaMultiselectable) { 
                                var index = scope.listboxData.indexOf(selectedItems[0]); 
                                var indies = elementsIndies.filter(function(item) {
                                    return (item === index);
                                });

                                if (indies.length === 0 || indies[0] != index) {
                                    // Set focused on 0
                                    currentIndexSet.elementIndex = elementsIndies[0]; 
                                    currentIndexSet.listboxDataIndex = 0;
                                    focusOnElement(currentIndexSet.elementIndex);
                                } else {
                                    focusOnElement(indies[0]); 
                                    currentIndexSet.elementIndex = indies[0];
                                    currentIndexSet.listboxDataIndex = index;
                                }
                            } else { 
                                focusOnElement(currentIndexSet.elementIndex);  
                            }
                            scope.currentIndex = currentIndexSet.listboxDataIndex;

                            if (!scope.$$phase) {
                                scope.$apply();
                            }
                        });
                        elem.bind('keyup', function(evt) {
                            if (evt.keyCode === keymap.KEY.SHIFT) {
                                shiftKeyPressed = false;
                            } else if (evt.keyCode === keymap.KEY.CTRL) {
                                ctrlKeyPressed = false;
                            }
                        });
		
                        elem.bind('keydown', function(evt) {
                            var keyCode = evt.keyCode;
                            elements = elem.children();
                            if (keyCode === keymap.KEY.SHIFT) {
                                shiftKeyPressed = true;
                            } else if (evt.keyCode === keymap.KEY.CTRL) {
                                ctrlKeyPressed = true;
                            }

                            switch(keyCode) {
                                case 65: // A key
                                {
                                    if (scope.multiselectable && evt.ctrlKey) {
                                        var arr = scope.listboxData.filter(isTrue);
                                        var elementsIndies = Array.prototype.map.call(elements, function(item) {
                                            return parseInt(angular.element(item).attr('data-index'), 10);
                                        });
                                        var val = !(arr.length === scope.listboxData.length);
                                        for (var i = 0; i < elementsIndies.length; i++) {
                                            scope.listboxData[elementsIndies[i]].selected = val;
                                        }

                                        if (!scope.$$phase) {
                                            scope.$apply();
                                        }
                                        
                                        evt.preventDefault();
                                        evt.stopPropagation();
                                    }
                                    break;
                                }
                                case keymap.KEY.END:
                                {
                                    if (scope.multiselectable && evt.ctrlKey && evt.shiftKey) {
                                        var elementsIndies = Array.prototype.map.call(elements, function(item) {
                                            return parseInt(angular.element(item).attr('data-index'), 10);
                                        }).filter(function(item) {
                                            return (item >= currentIndexSet.listboxDataIndex);
                                        });
                                        for (var i = 0; i < elementsIndies.length; i++) {
                                            scope.listboxData[elementsIndies[i]].selected = true;
                                        }
                                        evt.preventDefault();
                                        evt.stopPropagation();

                                        if (!scope.$$phase) {
                                            scope.$apply();
                                        }
                                    }
                                    break;
                                }
                                case keymap.KEY.HOME: 
                                {
                                    if (scope.multiselectable && evt.ctrlKey && evt.shiftKey) {
                                        selectItems(0, currentIndexSet.listboxDataIndex+1, true); // currentIndex+1 is what is being focused on
                                        evt.preventDefault();
                                        evt.stopPropagation();
                                    }
                                    break;
                                }
                                case keymap.KEY.LEFT:
                                case keymap.KEY.UP:
                                {
                                    if (currentIndexSet.listboxDataIndex === 0) {
                                        evt.preventDefault();
                                        evt.stopPropagation();
                                        return;
                                    }

                                    decrementIndex(elements.eq(currentIndexSet.elementIndex));
                                    if (scope.multiselectable && (evt.shiftKey || evt.ctrlKey)) {
                                        if (evt.shiftKey) {
                                            if (prevDirection === 'DOWN') {
                                                scope.listboxData[currentIndexSet.listboxDataIndex+1].selected = !scope.listboxData[currentIndexSet.listboxDataIndex+1].selected;
                                            }
                                            scope.listboxData[currentIndexSet.listboxDataIndex].selected = !scope.listboxData[currentIndexSet.listboxDataIndex].selected;
                                        }
                                        prevDirection = 'UP';
                                    } else {
                                        // If no modifier keys are selected, all other items need to be unselected.
                                        prevDirection = undefined;
                                        selectItems(0, scope.listboxData.length, false);
                                        if(currentIndexSet.listboxDataIndex !== undefined && !isNaN(currentIndexSet.listboxDataIndex)){
                                            scope.listboxData[currentIndexSet.listboxDataIndex].selected = true;
                                        }
                                    }
                                    focusOnElement(currentIndexSet.elementIndex);
                                    if(!scope.$$phase) {
                                        scope.$apply();
                                    }
                                    evt.preventDefault();
                                    evt.stopPropagation();
                                    break;
                                }
                                case keymap.KEY.RIGHT:
                                case keymap.KEY.DOWN:
                                {
                                    if (currentIndexSet.listboxDataIndex === scope.listboxData.length-1) {
                                        evt.preventDefault();
                                        evt.stopPropagation();
                                        return;
                                    }

                                    incrementIndex(elements.eq(currentIndexSet.elementIndex));
                                    
                                    if (scope.multiselectable && (evt.shiftKey || evt.ctrlKey)) {
                                        if (evt.shiftKey) {
                                            if (prevDirection === 'UP') {
                                                scope.listboxData[currentIndexSet.listboxDataIndex-1].selected = !scope.listboxData[currentIndexSet.listboxDataIndex-1].selected;
                                            }
                                            
                                            scope.listboxData[currentIndexSet.listboxDataIndex].selected = !scope.listboxData[currentIndexSet.listboxDataIndex].selected;    
                                        }
                                        prevDirection = 'DOWN';
                                    } else {
                                        // If no modifier keys are selected, all other items need to be unselected.
                                        prevDirection = undefined;
                                        selectItems(0, scope.listboxData.length, false);
                                        if(currentIndexSet.listboxDataIndex !== undefined && !isNaN(currentIndexSet.listboxDataIndex)){
                                            scope.listboxData[currentIndexSet.listboxDataIndex].selected = true;
                                        }
                                    }

                                    focusOnElement(currentIndexSet.elementIndex);
                                    if(!scope.$$phase) {
                                        scope.$apply();
                                    }
                                    evt.preventDefault();
                                    evt.stopPropagation();
                                    break;
                                }
                                case keymap.KEY.TAB:
                                    if(evt.shiftKey) {
                                        var previousElement = b2bDOMHelper.previousElement(elem.parent().parent(), true);
                                        evt.preventDefault();
                                        previousElement.focus();
                                    }
                                    break;
                                default:
                                    break;
                            }
                        });

                        elem.bind('click', function(evt) {
                            var index = parseInt(evt.target.dataset.index, 10);
                            if (index === undefined || isNaN(index)) {
                                return;
                            }
                            if (scope.multiselectable && currentIndexSet.listboxDataIndex !== undefined) {
                                if (shiftKeyPressed) {
                                    var min = Math.min(index, currentIndexSet.listboxDataIndex);
                                    var max = Math.max(index, currentIndexSet.listboxDataIndex);

                                    if (index === min) { // clicking up
                                        var firstIndex = scope.listboxData.some(function(item) { return item.selected == true; });
                                        // Given the firstIndex, let's find the matching element to get proper element match
                                        elements = elem.children();
                                        elements.eq(firstIndex)
                                        var elementsThatMatch = Array.prototype.filter.call(elements, function(item) {
                                            if (parseInt(angular.element(item).attr('data-index'), 10) === firstIndex) {
                                                return true;
                                            }
                                        });
                                        firstIndex = parseInt(angular.element(elementsThatMatch).attr('data-index'), 10);
                                        
                                        if (index <= firstIndex && scope.listboxData.filter(isTrue).length > 1) {
                                            // Break the selection into 2
                                            selectItems(firstIndex + 1, max + 1, undefined); // + 1 needed because selectItems only selects up to MAX
                                            selectItems(min, firstIndex, undefined); 
                                        } else if (scope.listboxData.filter(isTrue).length == 1){
                                            selectItems(min, max, undefined); 
                                        } else {
                                            selectItems(min + 1, max + 1, undefined);
                                        }
                                    } else { // clicking down
                                        selectItems(min + 1, max + 1, scope.listboxData[min].selected);
                                    }
                                } else if (ctrlKeyPressed) {
                                    scope.listboxData[index].selected = !scope.listboxData[index].selected;
                                } else {
                                    selectItems(0, scope.listboxData.length, false);
                                    scope.listboxData[index].selected = !scope.listboxData[index].selected;
                                }
                            } else {
                                selectItems(0, scope.listboxData.length, false);
                                scope.listboxData[index].selected = !scope.listboxData[index].selected;
                            }
                            currentIndexSet.elementIndex = index;
                            currentIndexSet.listboxDataIndex = index;
                            scope.currentIndex = currentIndexSet.listboxDataIndex;
                            if (!scope.$$phase) {
                                scope.$apply();
                            }
                            focusOnElement(index);
                        });
                    }
                };
            }]);
/**
 * @ngdoc directive
 * @name Videos, audio & animation.att:loaderAnimation
 *
 * @description
 *  <file src="src/loaderAnimation/docs/readme.md" />
 *
 * @usage
 *   <!-- Below demo js shows-->
 *   Angular library uses Global.css's icon-spinner.
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/loaderAnimation/docs/demo.html" />
            <file src="src/loaderAnimation/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.loaderAnimation', [])
    .constant('b2bSpinnerConfig', {
        loadingText: 'Loading...',
        startEvent: 'startButtonSpinner',
        stopEvent: 'stopButtonSpinner'
    })
    .constant("progressTrackerConfig", {
        loadingText: 'Loading...',
        minDuration: "",
        activationDelay: "",
        minDurationPromise: "",
        activationDelayPromise: ""
    })

.provider('progressTracker', function () {
    this.$get = ['$q', '$timeout', function ($q, $timeout) {
        function cancelTimeout(promise) {
            if (promise) {
                $timeout.cancel(promise);
            }
        }
        return function ProgressTracker(options) {
            //do new if user doesn't
            if (!(this instanceof ProgressTracker)) {
                return new ProgressTracker(options);
            }

            options = options || {};
            //Array of promises being tracked
            var tracked = [];
            var self = this;
            //Allow an optional "minimum duration" that the tracker has to stay active for.
            var minDuration = options.minDuration;
            //Allow a delay that will stop the tracker from activating until that time is reached
            var activationDelay = options.activationDelay;
            var minDurationPromise;
            var activationDelayPromise;
            self.active = function () {
                //Even if we have a promise in our tracker, we aren't active until delay is elapsed
                if (activationDelayPromise) {
                    return false;
                }
                return tracked.length > 0;
            };
            self.tracking = function () {
                //Even if we aren't active, we could still have a promise in our tracker
                return tracked.length > 0;
            };
            self.destroy = self.cancel = function () {
                minDurationPromise = cancelTimeout(minDurationPromise);
                activationDelayPromise = cancelTimeout(activationDelayPromise);
                for (var i = tracked.length - 1; i >= 0; i--) {
                    tracked[i].resolve();
                }
                tracked.length = 0;
            };
            //Create a promise that will make our tracker active until it is resolved.
            // @return deferred - our deferred object that is being tracked
            self.createPromise = function () {
                var deferred = $q.defer();
                tracked.push(deferred);
                //If the tracker was just inactive and this the first in the list of promises, we reset our delay and minDuration again.
                if (tracked.length === 1) {
                    if (activationDelay) {
                        activationDelayPromise = $timeout(function () {
                            activationDelayPromise = cancelTimeout(activationDelayPromise);
                            startMinDuration();
                        }, activationDelay);
                    } else {
                        startMinDuration();
                    }
                }
                deferred.promise.then(onDone(false), onDone(true));
                return deferred;

                function startMinDuration() {
                    if (minDuration) {
                        minDurationPromise = $timeout(angular.noop, minDuration);
                    }
                }
                //Create a callback for when this promise is done. It will remove our tracked promise from the array if once minDuration is complete
                function onDone() {
                    return function () {
                        (minDurationPromise || $q.when()).then(function () {
                            var index = tracked.indexOf(deferred);
                            tracked.splice(index, 1);
                            //If this is the last promise, cleanup the timeouts for activationDelay
                            if (tracked.length === 0) {
                                activationDelayPromise = cancelTimeout(activationDelayPromise);
                            }
                        });
                    };
                }
            };
            self.addPromise = function (promise) {
                
//                we cannot assign then function in other var and then add the resolve and reject 
                var thenFxn = promise && (promise.then || promise.$then || (promise.$promise && promise.$promise.then));                
                if (!thenFxn) {
                    throw new Error("progressTracker expects a promise object :: Not found");
                }
                var deferred = self.createPromise();
                //When given promise is done, resolve our created promise
                //Allow $then for angular-resource objects

                promise.then(function (value) {
                        deferred.resolve(value);
                        return value;
                    }, function (value) {
                        deferred.reject(value);
                        return $q.reject(value);
                    }
                );
                return deferred;
            };
        };
    }];
})

.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push(['$q', 'progressTracker', function ($q) {
        return {
            request: function (config) {
                if (config.tracker) {
                    if (!angular.isArray(config.tracker)) {
                        config.tracker = [config.tracker];
                    }
                    config.$promiseTrackerDeferred = config.$promiseTrackerDeferred || [];

                    angular.forEach(config.tracker, function (tracker) {
                        var deferred = tracker.createPromise();
                        config.$promiseTrackerDeferred.push(deferred);
                    });
                }
                return $q.when(config);
            },
            response: function (response) {
                if (response.config && response.config.$promiseTrackerDeferred) {
                    angular.forEach(response.config.$promiseTrackerDeferred, function (deferred) {
                        deferred.resolve(response);
                    });
                }
                return $q.when(response);
            },
            responseError: function (response) {
                if (response.config && response.config.$promiseTrackerDeferred) {
                    angular.forEach(response.config.$promiseTrackerDeferred, function (deferred) {
                        deferred.reject(response);
                    });
                }
                return $q.reject(response);
            }
        };
    }]);
}])

.directive('b2bClickSpin', ['$timeout', '$parse', '$rootScope', 'progressTracker', function ($timeout, $parse, $rootScope, progressTracker) {
    return {
        restrict: 'A',
        link: function (scope, elm, attrs) {
            var fn = $parse(attrs.b2bClickSpin);
            elm.on('click', function (event) {
                var promise = $timeout(function () {console.log("inside Promise")}, 5000);
                scope.$apply(function () {
                    fn(scope, {
                        $event: event
                    });
                });
                //comment this line if not running unit test
                $rootScope.loadingTracker = progressTracker({
                    minDuration: 750
                });
                $rootScope.loadingTracker.addPromise(promise);
                angular.forEach("$routeChangeSuccess $viewContentLoaded $locationChangeSuccess".split(" "), function (event) {
                    $rootScope.$on(event, function () {

                        $timeout.cancel(promise);
                    });
                });
            });
        }
    };
}])

.directive('b2bProgressTracker', ['progressTrackerConfig', function (ptc) {
    return {
        restrict: 'EA',
        replace: true,
        template: '<div><div ng-show="loadingTracker.active()" style="width:100%; text-align:center"><i class=\"icon-spinner\"></i></div><div ng-show="loadingTracker.active()" style="width:100%;margin-top:10px; text-align:center">'+ ptc.loadingText+'</div></div>'
    };
}])

.directive('b2bLoadButton', ['b2bSpinnerConfig', '$timeout', function (spinnerConfig, $timeout) {
    var spinButton = function (state, element, data) {
        
        var attr = element.html() ? 'html' : 'val';
        state = state + 'Text';
        if (state === 'loadingText') {
            element[attr](data[state]);
            element.attr("disabled",'disabled');
            element.addClass('disabled');
        } else if (state === 'resetText') {
            element[attr](data[state]);
            element.removeAttr("disabled");
            element.removeClass('disabled');
        }
    };

    return {
        restrict: 'A',
        replace: false,
        scope: {
            promise: '=promise',
            startEvent: '@startEvent',
            stopEvent: '@stopEvent'
        },
        link: function (scope, element, attr) {
            var validAttr = element.html() ? 'html' : 'val';
            var data = {
                loadingText: '',
                resetText: ''
            };

            var updateLoadingText = function (val) {
                var loadingText = val;
                if (!angular.isDefined(loadingText) || loadingText === "") {
                    loadingText = spinnerConfig.loadingText;
                }
                data.loadingText = validAttr === 'html' ? "<i class=\"icon-spinner small\"></i>" + loadingText : loadingText;
            };
            var updateResetText = function (val) {
                data.resetText = val;
            };

            attr.$observe('b2bLoadButton', function (val) {
                updateLoadingText(val);
            });
            $timeout(function () {
                updateResetText(element[validAttr]());
            }, 500);

            if (!angular.isDefined(scope.startEvent) || scope.startEvent === "") {
                scope.startEvent = spinnerConfig.startEvent;
            }

            if (!angular.isDefined(scope.stopEvent) || scope.stopEvent === "") {
                scope.stopEvent = spinnerConfig.stopEvent;
            }

            scope.$watch('promise', function () {
                if (angular.isDefined(scope.promise) && angular.isFunction(scope.promise.then)) {
                    spinButton('loading', element, data);
                    scope.promise.then(function () {
                        spinButton('reset', element, data);
                    }, function () {
                        spinButton('reset', element, data);
                    });
                }
            });

            scope.$on(scope.startEvent, function () {
                spinButton('loading', element, data);
                scope.$on(scope.stopEvent, function () {
                    spinButton('reset', element, data);
                });
            });
        }
    };
}]);
 /**
 * @ngdoc directive
 * @name Misc.att:messageWrapper
 * @scope
 * @param {boolean} trigger - A boolean that triggers directive to switch focus
 * @param {integer} delay  - Extra delay added to trigger code to allow for DOM to be ready. Default is 10ms.
 * @param {string} noFocus - Attribute-based API to trigger whether first focusable element receives focus on trigger or whole message (assumes tabindex="-1" set on first child)
 * @param {string} trapFocus - Attribute-based API to trap focus within the message. This should be enabled by default on all toast messages.
 * @description
 *  <file src="src/messageWrapper/docs/readme.md" />
 * @usage
 * <b2b-message-wrapper>Code that contains at least one focusable element and will be shown/hidden on some logic. This must have tabindex="-1".</b2b-message-wrapper>
 *
 * @example
 *  <section id="code">   
 <b>HTML + AngularJS</b>
 <example module="b2b.att">
 <file src="src/messageWrapper/docs/demo.html" />
 <file src="src/messageWrapper/docs/demo.js" />
 </example>
 </section>
 *
 */
angular.module('b2b.att.messageWrapper', ['b2b.att.utilities'])
.directive('b2bMessageWrapper', ['b2bDOMHelper', '$compile', '$timeout', '$log', function(b2bDOMHelper, $compile, $timeout, $log) {
  return {
    restrict: 'AE',
    scope: {
      trigger: '=',
      delay: '=?'
    },
    transclude: true,
    replace: true,
    template: '<div ng-transclude></div>',
    link: function(scope, elem, attrs) {
      scope.delay = scope.delay || 10;

      if (attrs.trapFocus != undefined && !elem.children().eq(0).attr('b2b-trap-focus-inside-element')) {
        // Append b2bTrapFocusInsideElement onto first child and recompile
        elem.children().eq(0).attr('b2b-trap-focus-inside-element', 'false');
        elem.children().eq(0).attr('trigger', scope.trigger);
        $compile(elem.contents())(scope);
      }

      var firstElement = undefined,
          launchingElement = undefined;
      
      scope.$watch('trigger', function(oldVal, newVal) {
        if (oldVal === newVal) return;
        if (!angular.isDefined(launchingElement)) {
          launchingElement = document.activeElement;
        }
        $timeout(function() {
          if (scope.trigger) {

            if (attrs.noFocus === true || attrs.noFocus === "") {
              elem.children()[0].focus();
            } else {
              firstElement = b2bDOMHelper.firstTabableElement(elem);

              if (angular.isDefined(firstElement)) {
                firstElement.focus();
              }
            }
            
          } else {
            if (angular.isDefined(launchingElement) && launchingElement.nodeName !== 'BODY') {
              if (launchingElement === document.activeElement) {
                return;
              }

              if (b2bDOMHelper.isInDOM(launchingElement) && b2bDOMHelper.isTabable(launchingElement)) {
                  // At this point, launchingElement is still a valid element, but focus will fail and 
                  // activeElement will become body, hence we want to apply custom logic and find previousElement
                  var prevLaunchingElement = launchingElement;
                  launchingElement.focus();

                  if (document.activeElement !== launchingElement || document.activeElement.nodeName === 'BODY') {
                    launchingElement = b2bDOMHelper.previousElement(angular.element(prevLaunchingElement), true);
                    launchingElement.focus();
                  }
              } else {
                launchingElement = b2bDOMHelper.previousElement(launchingElement, true);
                launchingElement.focus();
              }
            }
          }
        }, scope.delay); 
      });
    }
  };
}]);
/**
 * @ngdoc directive
 * @name Messages, modals & alerts.att:modalsAndAlerts
 *
 * @description
 *  <file src="src/modalsAndAlerts/docs/readme.md" />
 *
 * @usage
 *  <button class="btn" b2b-modal="b2bTemplate/modalsAndAlerts/demo_modal.html" modal-ok="ok()" modal-cancel="cancel()">Launch demo modal</button>
 *
 * @example
 *  <section id="code">
     <example module="b2b.att">
      <file src="src/modalsAndAlerts/docs/demo.html" />
      <file src="src/modalsAndAlerts/docs/demo.js" />
     </example>
    </section>
 *
 */
angular.module('b2b.att.modalsAndAlerts', ['b2b.att.position', 'b2b.att.transition', 'b2b.att.utilities'])

/**
 * A helper, internal data structure that acts as a map but also allows getting / removing
 * elements in the LIFO order
 */
.factory('$$stackedMap', function () {
    return {
        createNew: function () {
            var stack = [];

            return {
                add: function (key, value) {
                    stack.push({
                        key: key,
                        value: value
                    });
                },
                get: function (key) {
                    for (var i = 0; i < stack.length; i++) {
                        if (key === stack[i].key) {
                            return stack[i];
                        }
                    }
                },
                keys: function () {
                    var keys = [];
                    for (var i = 0; i < stack.length; i++) {
                        keys.push(stack[i].key);
                    }
                    return keys;
                },
                top: function () {
                    return stack[stack.length - 1];
                },
                remove: function (key) {
                    var idx = -1;
                    for (var i = 0; i < stack.length; i++) {
                        if (key === stack[i].key) {
                            idx = i;
                            break;
                        }
                    }
                    return stack.splice(idx, 1)[0];
                },
                removeTop: function () {
                    return stack.splice(stack.length - 1, 1)[0];
                },
                length: function () {
                    return stack.length;
                }
            };
        }
    };
}).factory('trapFocusInElement', ['$document', '$isElement', 'b2bDOMHelper', 'keymap', function ($document, $isElement, b2bDOMHelper, keymap) {
    var elementStack = [];
    var stackHead = undefined;
    var firstTabableElement, lastTabableElement;

    var trapKeyboardFocusInFirstElement = function (e) {
        if (!e.keyCode) {
            e.keyCode = e.which;
        }

        if (e.shiftKey === true && e.keyCode === keymap.KEY.TAB) {
            lastTabableElement[0].focus();
            e.preventDefault(e);
            e.stopPropagation(e);
        }

    };

    var trapKeyboardFocusInLastElement = function (e) {
        if (!e.keyCode) {
            e.keyCode = e.which;
        }

        if (e.shiftKey === false && e.keyCode === keymap.KEY.TAB) {
            firstTabableElement[0].focus();
            e.preventDefault(e);
            e.stopPropagation(e);
        }
    };
    
    var trapFocusInElement = function (flag, firstTabableElementParam, lastTabableElementParam) {
        var bodyElements = $document.find('body').children();

        firstTabableElement = firstTabableElementParam ? firstTabableElementParam : angular.element(b2bDOMHelper.firstTabableElement(stackHead));
        lastTabableElement = lastTabableElementParam ? lastTabableElementParam : angular.element(b2bDOMHelper.lastTabableElement(stackHead));

        if (flag) {
            for (var i = 0; i < bodyElements.length; i++) {
                if (bodyElements[i] !== stackHead[0]) {
                    bodyElements.eq(i).attr('aria-hidden', true);
                }
            }
            firstTabableElement.bind('keydown', trapKeyboardFocusInFirstElement);
            lastTabableElement.bind('keydown', trapKeyboardFocusInLastElement);
        } else {
            for (var j = 0; j < bodyElements.length; j++) {
                if (bodyElements[j] !== stackHead[0]) {
                    bodyElements.eq(j).removeAttr('aria-hidden');
                }
            }
            firstTabableElement.unbind('keydown', trapKeyboardFocusInFirstElement);
            lastTabableElement.unbind('keydown', trapKeyboardFocusInLastElement);
        }
    };
    var toggleTrapFocusInElement = function (flag, element) {
        if (angular.isDefined(flag) && angular.isDefined(element)) {
            if (angular.isUndefined(stackHead)) {
                stackHead = element;
                trapFocusInElement(flag);
            } else {
                if (flag) {
                    trapFocusInElement(false);
                    elementStack.push(stackHead);
                    stackHead = element;
                    trapFocusInElement(true);
                } else {
                    if (stackHead.prop('$$hashKey') === element.prop('$$hashKey')) {
                        trapFocusInElement(false);
                        stackHead = elementStack.pop();
                        if (angular.isDefined(stackHead)) {
                            trapFocusInElement(true);
                        }
                    }
                }
            }
        }else {
            if (angular.isDefined(stackHead)) {
                trapFocusInElement(false, firstTabableElement, lastTabableElement);
                trapFocusInElement(true);
            }
        }
    };

    return toggleTrapFocusInElement;
}])

/**
 * A helper directive for the $modal service. It creates a backdrop element.
 */
.directive('b2bModalBackdrop', ['$modalStack', '$timeout', function ($modalStack, $timeout) {
    return {
        restrict: 'EA',
        replace: true,
        templateUrl: 'b2bTemplate/modalsAndAlerts/b2b-backdrop.html',
        link: function (scope, element, attrs) {
            scope.close = function (evt) {
                var modal = $modalStack.getTop();
                if (modal && modal.value.backdrop && modal.value.backdrop !== 'static') {
                    evt.preventDefault();
                    evt.stopPropagation();
                    $modalStack.dismiss(modal.key, 'backdrop click');
                }
            };
        }
    };
}])

.directive('b2bModalWindow', ['$timeout', 'windowOrientation', '$window', 'keymap', function ($timeout, windowOrientation, $window, keymap) {
    return {
        restrict: 'EA',
        scope: {
            index: '@'
        },
        replace: true,
        transclude: true,
        templateUrl: 'b2bTemplate/modalsAndAlerts/b2b-window.html',
        controller: ['$scope', '$element', '$attrs', function (scope, element, attrs) {
            scope.windowClass = attrs.windowClass || '';
            scope.sizeClass = attrs.sizeClass || '';
            scope.isNotifDialog = false;
            scope.modalClose = attrs.modalClose || false;

            this.setTitle = function (title) {
                scope.title = title;
            };
            this.setContent = function (content) {
                scope.content = content;
                scope.isNotifDialog = true;
            };
            this.isDockedModal = scope.windowClass.indexOf('modal-docked') > -1;
        }],
        link: function (scope, element, attrs, ctrl) {
            if (ctrl.isDockedModal) {
                scope.isModalLandscape = false;

                var window = angular.element($window);
                scope.updateCss = function () {
                    if (windowOrientation.isPotrait()) { // Potrait Mode
                        scope.isModalLandscape = false;
                    } else if (windowOrientation.isLandscape()) { // Landscape Mode
                        scope.isModalLandscape = true;
                    }
                };

                $timeout(function () {
                    scope.updateCss();
                    scope.$apply();
                }, 100);
                window.bind('orientationchange', function () {
                    scope.updateCss();
                    scope.$apply();
                });
                window.bind('resize', function () {
                    scope.updateCss();
                    scope.$apply();
                });
            }else {
                angular.element(element[0].querySelectorAll(".b2b-dropdown-desktop-list")).css({
                    "max-height": "200px"
                });
            }

            var isIE = /msie|trident/i.test(navigator.userAgent);
            if (isIE) {
                if(angular.element(element[0].querySelector('.corner-button button.close')).length > 0){
                    angular.element(element[0].querySelector('.corner-button button.close')).bind('focus', function () {
                       angular.element(element[0].querySelector('.b2b-modal-header'))[0].scrollLeft = 0;
                       angular.element(element[0].querySelector('.b2b-modal-header'))[0].scrollTop = 0;
                    });
                }
            }

            if(scope.modalClose){
                element.bind('keydown', function (e) {
                    if(e.keyCode == keymap.KEY.ESC){
                        e.preventDefault();
                        e.stopPropagation();
                    }
                });
            }
        }
    };
}])

.directive('b2bModalTitle', [function () {
    return {
        restrict: 'A',
        require: '^b2bModalWindow',
        link: function (scope, elem, attr, ctrl) {
            ctrl.setTitle(attr.id);
        }
    };
}])

.directive('b2bModalContent', [function () {
    return {
        restrict: 'A',
        require: '^b2bModalWindow',
        link: function (scope, elem, attr, ctrl) {
            ctrl.setContent(attr.id);
        }
    };
}])


.directive('b2bModalBody', ['$timeout', '$position', '$document', '$window', 'windowOrientation', 'b2bAwdBreakpoints', function ($timeout, $position, $document, $window, windowOrientation, b2bAwdBreakpoints) {
    return {
        restrict: 'AC',
        scope: {
            index: '@'
        },
        require: '^b2bModalWindow',
        link: function (scope, element, attrs, ctrl) {
            var window = angular.element($window);
            var body = $document.find('body').eq(0);
            scope.setModalHeight = function () {
                var modalHeaderHeight, modalFooterHeight, modalBodyHeight, windowHeight, windowWidth, modalHeight;
                modalHeaderHeight = 0;
                modalFooterHeight = 0;
                windowHeight = $window.innerHeight;
                windowWidth = $window.innerWidth;
                body.css({
                    'height': windowHeight + 'px'
                });

                if (ctrl.isDockedModal) {
                    var modalElements = element.parent().children();
                    for (var i = 0; i < modalElements.length; i++) {
                        if (modalElements.eq(i).hasClass('b2b-modal-header')) {
                            modalHeaderHeight = $position.position(modalElements.eq(i)).height;
                        } else if (modalElements.eq(i).hasClass('b2b-modal-footer')) {
                            modalFooterHeight = $position.position(modalElements.eq(i)).height;
                        }
                    }

                    var el = element[0];
                    while((el = el.parentElement) && !el.classList.contains('modal') && !el.classList.contains('fade') && !el.classList.contains('in'));
                    if (el !== document.documentElement) {
                        modalHeight = el.getBoundingClientRect().height
                    } else {
                        modalHeight = $position.position(element.parent()).height;
                    }

                    modalBodyHeight = modalHeight - (modalHeaderHeight + modalFooterHeight) + 'px';

                    if (windowOrientation.isPotrait()) { // Potrait Mode
                        element.removeAttr('style').css({
                            height: modalBodyHeight
                        });
                    } else if (windowOrientation.isLandscape() && windowWidth < b2bAwdBreakpoints.breakpoints.mobile.max) { // Landscape Mode Mobile
                        element.removeAttr('style');
                    } else if (windowOrientation.isLandscape() && windowWidth >= b2bAwdBreakpoints.breakpoints.mobile.max) { // Landscape Mode Non-Mobile
                        element.removeAttr('style').css({
                            height: modalBodyHeight
                        });
                    }
                }
            };

            $timeout(function () {
                scope.setModalHeight();
                scope.$apply();
            }, 100);
            window.bind('orientationchange', function () {
                scope.setModalHeight();
                scope.$apply();
            });
            window.bind('resize', function () {
                scope.setModalHeight();
                scope.$apply();
            });
        }
    };
}])

.directive('b2bModalFooter', ['windowOrientation', '$window', function (windowOrientation, $window) {
    return {
        restrict: 'AC',
        scope: {
            index: '@'
        },
        link: function (scope, element, attrs) {

        }
    };
}])

.factory('$modalStack', ['$document', '$compile', '$rootScope', '$$stackedMap', '$log', '$timeout', 'trapFocusInElement', function ($document, $compile, $rootScope, $$stackedMap, $log, $timeout, trapFocusInElement) {
    var backdropjqLiteEl, backdropDomEl;
    var backdropScope = $rootScope.$new(true);
    var body = $document.find('body').eq(0);
    var html = $document.find('html').eq(0);
    var openedWindows = $$stackedMap.createNew();
    var $modalStack = {};

    function backdropIndex() {
        var topBackdropIndex = -1;
        var opened = openedWindows.keys();
        for (var i = 0; i < opened.length; i++) {
            if (openedWindows.get(opened[i]).value.backdrop) {
                topBackdropIndex = i;
            }
        }
        return topBackdropIndex;
    }

    $rootScope.$watch(backdropIndex, function (newBackdropIndex) {
        backdropScope.index = newBackdropIndex;
    });

    function removeModalWindow(modalInstance) {
        //background scroll fix
        html.removeAttr('style');
        body.removeAttr('style');
        body.removeClass('styled-by-modal');

        var modalWindow = openedWindows.get(modalInstance).value;
        trapFocusInElement(false, modalWindow.modalDomEl);

        //clean up the stack
        openedWindows.remove(modalInstance);

        //remove window DOM element
        modalWindow.modalDomEl.remove();

        //remove backdrop if no longer needed
        if (backdropDomEl && backdropIndex() === -1) {
            backdropDomEl.remove();
            backdropDomEl = undefined;
        }

        //destroy scope
        modalWindow.modalScope.$destroy();
    }

    $document.bind('keydown', function (evt) {
        var modal;

        if (evt.which === 27) {
            modal = openedWindows.top();
            if (modal && modal.value.keyboard) {
                $rootScope.$apply(function () {
                    $modalStack.dismiss(modal.key);
                });
            }
        }
    });

    $modalStack.open = function (modalInstance, modal) {

        openedWindows.add(modalInstance, {
            deferred: modal.deferred,
            modalScope: modal.scope,
            backdrop: modal.backdrop,
            keyboard: modal.keyboard
        });

        var angularDomEl = angular.element('<div b2b-modal-window></div>');
        angularDomEl.attr('window-class', modal.windowClass);
        angularDomEl.attr('size-class', modal.sizeClass);
        angularDomEl.attr('index', openedWindows.length() - 1);
        angularDomEl.attr('modal-close', modal.modalClose);
        angularDomEl.html(modal.content);

        var modalDomEl = $compile(angularDomEl)(modal.scope);
        openedWindows.top().value.modalDomEl = modalDomEl;
        //background page scroll fix
        html.css({
            'overflow-y': 'hidden'
        });
        body.css({
            'overflow-y': 'hidden',
            'width': '100%',
            'height': window.innerHeight + 'px'
        });
        body.addClass('styled-by-modal');
        body.append(modalDomEl);

        if (backdropIndex() >= 0 && !backdropDomEl) {
            backdropjqLiteEl = angular.element('<div b2b-modal-backdrop></div>');
            backdropDomEl = $compile(backdropjqLiteEl)(backdropScope);
            body.append(backdropDomEl);
        }

        $timeout(function () {

            if (modal.scope.$$childHead.isNotifDialog) {
                angular.element(modalDomEl).find('button')[0].focus();
            } else {
                angular.element(modalDomEl)[0].focus();
            }
            trapFocusInElement(true, angular.element(modalDomEl).eq(0));
        }, 200);
    };

    $modalStack.close = function (modalInstance, result) {
        var modal = openedWindows.get(modalInstance);
        if (modal) {
            modal.value.deferred.resolve(result);
            removeModalWindow(modalInstance);
        }
    };

    $modalStack.dismiss = function (modalInstance, reason) {
        var modalWindow = openedWindows.get(modalInstance).value;
        if (modalWindow) {
            modalWindow.deferred.reject(reason);
            removeModalWindow(modalInstance);
        }
    };

    $modalStack.getTop = function () {
        return openedWindows.top();
    };

    return $modalStack;
}])

.provider('$modal', function () {
    var $modalProvider = {
        options: {
            backdrop: true, //can be also false or 'static'
            keyboard: true
        },
        $get: ['$injector', '$rootScope', '$q', '$http', '$templateCache', '$controller', '$modalStack', function ($injector, $rootScope, $q, $http, $templateCache, $controller, $modalStack) {
            var $modal = {};

            function getTemplatePromise(options) {
                return options.template ? $q.when(options.template) :
                    $http.get(options.templateUrl, {
                        cache: $templateCache
                    }).then(function (result) {
                        return result.data;
                    });
            }

            function getResolvePromises(resolves) {
                var promisesArr = [];
                angular.forEach(resolves, function (value, key) {
                    if (angular.isFunction(value) || angular.isArray(value)) {
                        promisesArr.push($q.when($injector.invoke(value)));
                    }
                });
                return promisesArr;
            }

            $modal.open = function (modalOptions) {

                var modalResultDeferred = $q.defer();
                var modalOpenedDeferred = $q.defer();
                //prepare an instance of a modal to be injected into controllers and returned to a caller
                var modalInstance = {
                    result: modalResultDeferred.promise,
                    opened: modalOpenedDeferred.promise,
                    close: function (result) {
                        $modalStack.close(modalInstance, result);
                    },
                    dismiss: function (reason) {
                        $modalStack.dismiss(modalInstance, reason);
                    }
                };

                //merge and clean up options
                modalOptions = angular.extend({}, $modalProvider.options, modalOptions);
                modalOptions.resolve = modalOptions.resolve || {};

                //verify options
                if (!modalOptions.template && !modalOptions.templateUrl) {
                    throw new Error('One of template or templateUrl options is required.');
                }

                var templateAndResolvePromise =
                    $q.all([getTemplatePromise(modalOptions)].concat(getResolvePromises(modalOptions.resolve)));


                templateAndResolvePromise.then(function resolveSuccess(tplAndVars) {

                    var modalScope = (modalOptions.scope || $rootScope).$new();
                    modalScope.$close = modalInstance.close;
                    modalScope.$dismiss = modalInstance.dismiss;

                    var ctrlInstance, ctrlLocals = {};
                    var resolveIter = 1;

                    //controllers
                    if (modalOptions.controller) {
                        ctrlLocals.$scope = modalScope;
                        ctrlLocals.$modalInstance = modalInstance;
                        angular.forEach(modalOptions.resolve, function (value, key) {
                            ctrlLocals[key] = tplAndVars[resolveIter++];
                        });

                        ctrlInstance = $controller(modalOptions.controller, ctrlLocals);
                    }

                    $modalStack.open(modalInstance, {
                        scope: modalScope,
                        deferred: modalResultDeferred,
                        content: tplAndVars[0],
                        backdrop: modalOptions.backdrop,
                        keyboard: modalOptions.keyboard,
                        windowClass: modalOptions.windowClass,
                        sizeClass: modalOptions.sizeClass,
                        modalClose: modalOptions.modalClose
                    });

                }, function resolveError(reason) {
                    modalResultDeferred.reject(reason);
                });

                templateAndResolvePromise.then(function () {
                    modalOpenedDeferred.resolve(true);
                }, function () {
                    modalOpenedDeferred.reject(false);
                });

                return modalInstance;
            };

            return $modal;
        }]
    };

    return $modalProvider;
})

.directive("b2bModal", ["$modal", "$log", '$scrollTo', function ($modal, $log, $scrollTo) {
    return {
        restrict: 'A',
        scope: {
            b2bModal: '@',
            modalController: '@',
            modalOk: '&',
            modalCancel: '&',
            windowClass: '@',
            sizeClass: '@', 
            modalClose: '@'
        },
        link: function (scope, elm, attr) {
            elm.bind('click', function (ev) {
                var currentPosition = ev.pageY - ev.clientY;
                ev.preventDefault();
                if (angular.isDefined(elm.attr("href")) && elm.attr("href") !== "") {
                    scope.b2bModal = elm.attr("href");
                }
                $modal.open({
                    templateUrl: scope.b2bModal,
                    controller: scope.modalController,
                    windowClass: scope.windowClass,
                    sizeClass: scope.sizeClass, 
                    modalClose: scope.modalClose
                }).result.then(function (value) {
                    scope.modalOk({
                        value: value
                    });
                    elm[0].focus();
                }, function (value) {
                    scope.modalCancel({
                        value: value
                    });
                    elm[0].focus();
                });
            });
        }
    };
}])

.directive("utilityFilter", ["$modal", "$log", '$scrollTo', function ($modal, $log, $scrollTo) {
    return {
        restrict: 'EA',
        scope: {
            utilityFilter: '@'
        },
        require: 'ngModel',
        templateUrl: 'b2bTemplate/modal/u-filter.html',
        link: function (scope, element, attribute, ctrl) {
            //controller to be passed to $modal service
            scope.options = angular.copy(scope.$parent.$eval(attribute.ngModel));
            scope.$parent.$watch(attribute.ngModel, function (newVal, oldVal) {
                if (newVal !== oldVal) {
                    scope.options = newVal;
                }
            });
            var modalCtrl = function ($scope, options) {
                $scope.options = angular.copy(options);
            };

            if (angular.isDefined(scope.utilityFilter)) {
                scope.templateUrl = scope.utilityFilter;
            } else {
                scope.templateUrl = 'b2bTemplate/modal/u-filter-window.html';
            }
            element.bind('click', function (ev) {
                var currentPosition = ev.pageY - ev.clientY;
                $modal.open({
                    templateUrl: scope.templateUrl,
                    controller: modalCtrl,
                    resolve: {
                        options: function () {
                            return scope.options;
                        }
                    }
                }).result.then(function (value) {
                    ctrl.$setViewValue(value);
                    element[0].focus();
                    $scrollTo(0, currentPosition, 0);
                }, function () {
                    element[0].focus();
                    $scrollTo(0, currentPosition, 0);
                });
            });
        }
    };
}]);
/**
 * @ngdoc directive
 * @name Forms.att:monthSelector
 *
 * @description
 *  <file src="src/monthSelector/docs/readme.md" />
 *
 * @usage
 * <div b2b-monthpicker ng-model="dt" min="minDate" max="maxDate" mode="monthpicker"></div>
    
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/monthSelector/docs/demo.html" />
            <file src="src/monthSelector/docs/demo.js" />
        </example>
    </section>
 *
 */
angular.module('b2b.att.monthSelector', ['b2b.att.position', 'b2b.att.utilities'])

.constant('b2bMonthpickerConfig', {
    dateFormat: 'MM/dd/yyyy',
    dayFormat: 'd',
    monthFormat: 'MMMM',
    yearFormat: 'yyyy',
    dayHeaderFormat: 'EEEE',
    dayTitleFormat: 'MMMM yyyy',
    disableWeekend: false,
    disableSunday: false,
    disableDates: null,
    onSelectClose: null,
    startingDay: 0,
    minDate: null,
    maxDate: null,
    dueDate: null,
    fromDate: null,
    legendIcon: null,
    legendMessage: null,
    calendarDisabled: false,
    collapseWait: 0,
    orientation: 'left',
    inline: false,
    mode:0,
    helperText: 'The date you selected is $date. Double tap to open calendar. Select a date to close the calendar.',
    descriptionText: 'Use tab to navigate between previous button, next button and month. Use arrow keys to navigate between months. Use space or enter to select a month.',
    MonthpickerEvalAttributes: ['dateFormat', 'dayFormat', 'monthFormat', 'yearFormat', 'dayHeaderFormat', 'dayTitleFormat', 'disableWeekend', 'disableSunday', 'startingDay', 'collapseWait', 'orientation','mode','id'],
    MonthpickerWatchAttributes: ['min', 'max', 'due', 'from', 'legendIcon', 'legendMessage', 'ngDisabled'],
    MonthpickerFunctionAttributes: ['disableDates', 'onSelectClose']
})

.factory('b2bMonthpickerService', ['b2bMonthpickerConfig', 'dateFilter', function (b2bMonthpickerConfig, dateFilter) {
    var setAttributes = function (attr, elem) {
        if (angular.isDefined(attr) && attr !== null && angular.isDefined(elem) && elem !== null) {
            var attributes = b2bMonthpickerConfig.MonthpickerEvalAttributes.concat(b2bMonthpickerConfig.MonthpickerWatchAttributes, b2bMonthpickerConfig.MonthpickerFunctionAttributes);
            for (var key in attr) {
                var val = attr[key];
                if (attributes.indexOf(key) !== -1 && angular.isDefined(val)) {
                    elem.attr(key.toSnakeCase(), key);
                }
            }
        }
    };

    var bindScope = function (attr, scope) {
        if (angular.isDefined(attr) && attr !== null && angular.isDefined(scope) && scope !== null) {
            var evalFunction = function (key, val) {
                scope[key] = scope.$parent.$eval(val);
            };

            var watchFunction = function (key, val) {
                scope.$parent.$watch(val, function (value) {
                    scope[key] = value;
                });
                scope.$watch(key, function (value) {
                    scope.$parent[val] = value;
                });
            };

            var evalAttributes = b2bMonthpickerConfig.MonthpickerEvalAttributes;
            var watchAttributes = b2bMonthpickerConfig.MonthpickerWatchAttributes;
            for (var key in attr) {
                var val = attr[key];
                if (evalAttributes.indexOf(key) !== -1 && angular.isDefined(val)) {
                    evalFunction(key, val);
                } else if (watchAttributes.indexOf(key) !== -1 && angular.isDefined(val)) {
                    watchFunction(key, val);
                }
            }
        }
    };

    return {
        setAttributes: setAttributes,
        bindScope: bindScope
    };
}])

.controller('b2bMonthpickerController', ['$scope', '$attrs', 'dateFilter', '$element', '$position', 'b2bMonthpickerConfig', function ($scope, $attrs, dateFilter, $element, $position, dtConfig) {
    var format = {
            date: getValue($attrs.dateFormat, dtConfig.dateFormat),
            day: getValue($attrs.dayFormat, dtConfig.dayFormat),
            month: getValue($attrs.monthFormat, dtConfig.monthFormat),
            year: getValue($attrs.yearFormat, dtConfig.yearFormat),
            dayHeader: getValue($attrs.dayHeaderFormat, dtConfig.dayHeaderFormat),
            dayTitle: getValue($attrs.dayTitleFormat, dtConfig.dayTitleFormat),
            disableWeekend: getValue($attrs.disableWeekend, dtConfig.disableWeekend),
            disableSunday: getValue($attrs.disableSunday, dtConfig.disableSunday),
            disableDates: getValue($attrs.disableDates, dtConfig.disableDates)
        },
        startingDay = getValue($attrs.startingDay, dtConfig.startingDay);

    $scope.minDate = dtConfig.minDate ? $scope.resetTime(dtConfig.minDate) : null;
    $scope.maxDate = dtConfig.maxDate ? $scope.resetTime(dtConfig.maxDate) : null;
    $scope.dueDate = dtConfig.dueDate ? $scope.resetTime(dtConfig.dueDate) : null;
    $scope.fromDate = dtConfig.fromDate ? $scope.resetTime(dtConfig.fromDate) : null;
    $scope.legendIcon = dtConfig.legendIcon ? dtConfig.legendIcon : null;
    $scope.legendMessage = dtConfig.legendMessage ? dtConfig.legendMessage : null;
    $scope.ngDisabled = dtConfig.calendarDisabled ? dtConfig.calendarDisabled : null;
    $scope.collapseWait = getValue($attrs.collapseWait, dtConfig.collapseWait);
    $scope.orientation = getValue($attrs.orientation, dtConfig.orientation);
    $scope.onSelectClose = getValue($attrs.onSelectClose, dtConfig.onSelectClose);
    $scope.mode = getValue($attrs.mode, dtConfig.mode);
    
    $scope.inline = $attrs.inline === 'true' ? true : dtConfig.inline;

    function getValue(value, defaultValue) {
        return angular.isDefined(value) ? $scope.$parent.$eval(value) : defaultValue;
    }

    function getDaysInMonth(year, month) {
        return new Date(year, month, 0).getDate();
    }

    function getDates(startDate, n) {
        var dates = new Array(n);
        var current = startDate,
            i = 0;
        while (i < n) {
            dates[i++] = new Date(current);
            current.setDate(current.getDate() + 1);
        }
        return dates;
    }

    this.updatePosition = function (b2bMonthpickerPopupTemplate) {
        $scope.position = $position.offset($element);
        if($element.find('input').length > 0 ){
            $scope.position.top += $element.find('input').prop('offsetHeight');
        }else{
            $scope.position.top += $element.find('a').prop('offsetHeight');
        }
        
        if ($scope.orientation === 'right') {
            $scope.position.left -= (((b2bMonthpickerPopupTemplate && b2bMonthpickerPopupTemplate.prop('offsetWidth')) || 290) - $element.find('input').prop('offsetWidth'));
        }
    };

    function isSelected(dt) { 
        if (dt && angular.isDate($scope.currentDate) && compare(dt, $scope.currentDate) === 0) {
            return true;
        }
        return false;
    }

    function isFromDate(dt) {
        if (dt && angular.isDate($scope.fromDate) && compare(dt, $scope.fromDate) === 0) {
            return true;
        }
        return false;
    }

    function isDateRange(dt) {
        if (dt && $scope.fromDate && angular.isDate($scope.currentDate) && (compare(dt, $scope.fromDate) >= 0) && (compare(dt, $scope.currentDate) <= 0)) {
            return true;
        } else if (dt && $scope.fromDate && compare(dt, $scope.fromDate) === 0) {
            return true;
        }
        return false;
    }

    function isOld(date, currentMonthDate) {
        if (date && currentMonthDate && (new Date(date.getFullYear(), date.getMonth(), 1, 0, 0, 0).getTime() < new Date(currentMonthDate.getFullYear(), currentMonthDate.getMonth(), 1, 0, 0, 0).getTime())) {
            return true;
        } else {
            return false;
        }
    }

    function isNew(date, currentMonthDate) {
        if (date && currentMonthDate && (new Date(date.getFullYear(), date.getMonth(), 1, 0, 0, 0).getTime() > new Date(currentMonthDate.getFullYear(), currentMonthDate.getMonth(), 1, 0, 0, 0).getTime())) {
            return true;
        } else {
            return false;
        }
    }

    function isPastDue(dt) {
        if ($scope.dueDate) {
            return (dt > $scope.dueDate);
        }
        return false;
    }

    function isDueDate(dt) {
        if ($scope.dueDate) {
            return (dt.getTime() === $scope.dueDate.getTime());
        }
        return false;
    }

    var isDisabled = function (date, currentMonthDate) {
        if ($attrs.from && !angular.isDate($scope.fromDate)) {
            return true;
        }
        if (format.disableWeekend === true && (dateFilter(date, format.dayHeader) === "Saturday" || dateFilter(date, format.dayHeader) === "Sunday")) {
            return true;
        }
        if (format.disableSunday === true && (dateFilter(date, format.dayHeader) === "Sunday")) {
            return true;
        }
        if (isOld(date, currentMonthDate) || isNew(date, currentMonthDate)) {
            return true;
        }
        return (($scope.minDate && compare(date, $scope.minDate) < 0) || ($scope.maxDate && compare(date, $scope.maxDate) > 0) || (format.disableDates && format.disableDates({
            date: date
        })));
    };
    
    var isDisabledMonth = function (date, currentMonthDate) {
        if ($attrs.from && !angular.isDate($scope.fromDate)) {
            return true;
        }
        if (format.disableWeekend === true && (dateFilter(date, format.dayHeader) === "Saturday" || dateFilter(date, format.dayHeader) === "Sunday")) {
            return true;
        }
        if (format.disableSunday === true && (dateFilter(date, format.dayHeader) === "Sunday")) {
            return true;
        }
        return (($scope.minDate && compare(date, $scope.minDate) < 0) || ($scope.maxDate && compare(date, $scope.maxDate) > 0) || (format.disableDates && format.disableDates({
            date: date
        })));
    };    
         
    var compare = function (date1, date2) {
        return (new Date(date1.getFullYear(), date1.getMonth(), date1.getDate()) - new Date(date2.getFullYear(), date2.getMonth(), date2.getDate()));
    };

    function isMinDateAvailable(startDate, endDate) {
        if (($scope.minDate && $scope.minDate.getTime() >= startDate.getTime()) && ($scope.minDate.getTime() <= endDate.getTime())) {
            $scope.disablePrev = true;
            $scope.visibilityPrev = "hidden";
        } else {
            $scope.disablePrev = false;
            $scope.visibilityPrev = "visible";
        }
    }
    
    function isMaxDateAvailable(startDate, endDate) {
        if (($scope.maxDate && $scope.maxDate.getTime() >= startDate.getTime()) && ($scope.maxDate.getTime() <= endDate.getTime())) {
            $scope.disableNext = true;
            $scope.visibilityNext = "hidden";
        } else {
            $scope.disableNext = false;
            $scope.visibilityNext = "visible";
        }
    }    
    
    function isYearInRange(currentYear) {
            
        if ($scope.minDate && currentYear === $scope.minDate.getFullYear()) {
            $scope.disablePrev = true;
            $scope.visibilityPrev = "hidden";
        } else {
            $scope.disablePrev = false;
            $scope.visibilityPrev = "visible";
        }
        
        if ($scope.maxDate && currentYear === $scope.maxDate.getFullYear()) {
            $scope.disableNext = true;
            $scope.visibilityNext = "hidden";
        } else {
            $scope.disableNext = false;
            $scope.visibilityNext = "visible";
        }
        
    }    

    this.focusNextPrev = function(b2bMonthpickerPopupTemplate,init){
        if(init){
            if (!$scope.disablePrev){
                b2bMonthpickerPopupTemplate[0].querySelector('th.prev').focus();
            }else if (!$scope.disableNext){
                b2bMonthpickerPopupTemplate[0].querySelector('th.next').focus();
            }else{
                b2bMonthpickerPopupTemplate[0].querySelector('th.b2b-monthSelector-label').focus();
            }
        }else{
            if ($scope.disableNext || $scope.disablePrev){
                b2bMonthpickerPopupTemplate[0].querySelector('th.b2b-monthSelector-label').focus();
            }       
        }    
    };

    function getLabel(label) {
        if (label) {
            var labelObj = {
                pre: label.substr(0, 1).toUpperCase(),
                post: label
            };
            return labelObj;
        }
        return;
    }

    function makeDate(date, dayFormat, dayHeaderFormat, isSelected, isFromDate, isDateRange, isOld, isNew, isDisabled, dueDate, pastDue) {
        return {
            date: date,
            label: dateFilter(date, dayFormat),
            header: dateFilter(date, dayHeaderFormat),
            selected: !!isSelected,
            fromDate: !!isFromDate,
            dateRange: !!isDateRange,
            oldMonth: !!isOld,
            nextMonth: !!isNew,
            disabled: !!isDisabled,
            dueDate: !!dueDate,
            pastDue: !!pastDue,
            focusable: !((isDisabled && !(isSelected || isDateRange)) || (isOld || isNew))
        };
    }
    
    this.modes = [
        {
            name: 'day',
            getVisibleDates: function (date) {
                var year = date.getFullYear(),
                    month = date.getMonth(),
                    firstDayOfMonth = new Date(year, month, 1),
                    lastDayOfMonth = new Date(year, month + 1, 0);
                var difference = startingDay - firstDayOfMonth.getDay(),
                    numDisplayedFromPreviousMonth = (difference > 0) ? 7 - difference : -difference,
                    firstDate = new Date(firstDayOfMonth),
                    numDates = 0;

                if (numDisplayedFromPreviousMonth > 0) {
                    firstDate.setDate(-numDisplayedFromPreviousMonth + 1);
                    numDates += numDisplayedFromPreviousMonth; // Previous
                }
                numDates += getDaysInMonth(year, month + 1); // Current
                numDates += (7 - numDates % 7) % 7; // Next

                var days = getDates(firstDate, numDates),
                    labels = new Array(7);
                for (var i = 0; i < numDates; i++) {
                    var dt = new Date(days[i]);
                    days[i] = makeDate(dt,
                        format.day,
                        format.dayHeader,
                        isSelected(dt),
                        isFromDate(dt),
                        isDateRange(dt),
                        isOld(dt, date),
                        isNew(dt, date),
                        isDisabled(dt, date),
                        isDueDate(dt),
                        isPastDue(dt));
                }
                for (var j = 0; j < 7; j++) {
                    labels[j] = getLabel(dateFilter(days[j].date, format.dayHeader));
                }
                isMinDateAvailable(firstDayOfMonth, lastDayOfMonth);
                isMaxDateAvailable(firstDayOfMonth, lastDayOfMonth);
                return {
                    objects: days,
                    title: dateFilter(date, format.dayTitle),
                    labels: labels
                };
            },
            split: 7,
            step: {
                months: 1
            }
        },
        {
            name: 'month',
            getVisibleDates: function(date) {
                var months = [], 
                    labels = [], 
                    year = date.getFullYear();
                    for (var i = 0; i < 12; i++) {
                        var dt = new Date(year,i,1);                
                        months[i] = makeDate(dt,
                                    format.month,
                                    format.dayHeader,
                                    isSelected(dt), 
                                    isFromDate(dt),
                                    isDateRange(dt),
                                    false,
                                    false,
                                    isDisabledMonth(dt, date),
                                    isDueDate(dt),                                       
                                    isPastDue(dt));                                                                                                                                                         
                    }
                isYearInRange(year);  
                return {objects: months, title: dateFilter(date, format.year), labels: labels};
            },
            split:4,
            step: {years: 1}
        }
    ];
}])

.directive('b2bMonthpickerPopup', ['$parse', '$log', '$timeout', '$document', '$documentBind', '$isElement', '$templateCache', '$compile','$interval', 'trapFocusInElement', 'keymap', function ($parse, $log, $timeout, $document, $documentBind, $isElement, $templateCache, $compile, $interval,trapFocusInElement, keymap) {
    return {
        restrict: 'EA',
        scope: {
          trigger: '='
        },
        replace: true,
        transclude: true,
        templateUrl: function (elem, attr) {
            if (attr.inline === 'true') {
                return 'b2bTemplate/monthSelector/monthSelector-popup.html';
            }else if (attr.link === 'true') {
                return 'b2bTemplate/monthSelector/monthSelectorLink.html';
            }else {
                return 'b2bTemplate/monthSelector/monthSelector.html';
            }
        },
        scope: {},
        require: ['b2bMonthpickerPopup', 'ngModel', '?^b2bMonthpickerGroup'],
        controller: 'b2bMonthpickerController',
        link: function (scope, element, attrs, ctrls) {
            var MonthpickerCtrl = ctrls[0],
                ngModel = ctrls[1],
                b2bMonthpickerGroupCtrl = ctrls[2];
            var b2bMonthpickerPopupTemplate;

            if (!ngModel) {
                $log.error("ng-model is required.");
                return; // do nothing if no ng-model
            }

            // Configuration parameters
            var mode = scope.mode,
                selected;
            scope.isOpen = false;

            scope.headers = [];
            scope.footers = [];
            scope.triggerInterval=undefined;


            if (b2bMonthpickerGroupCtrl) {
                b2bMonthpickerGroupCtrl.registerMonthpickerScope(scope);
            }

            element.bind('keydown', function (ev) {                   
                if (!ev.keyCode) {
                    if (ev.which) {
                        ev.keyCode = ev.which;
                    } else if (ev.charCode) {
                        ev.keyCode = ev.charCode;
                    }
                }                                
                if(ev.keyCode === keymap.KEY.ESC)
                {
                    scope.isOpen = false;
                    toggleCalendar(scope.isOpen);
                    scope.$apply();
                }
            });
            
            element.find('button').bind('click', function () {
                onClicked();                
            });

            element.find('a').bind('click', function () {
                onClicked();                
            });

            
            element.find('input').bind('click', function () {
                onClicked();
            });

            var onClicked = function() {        
                if (!scope.ngDisabled) {
                    scope.isOpen = !scope.isOpen;
                    toggleCalendar(scope.isOpen);                    
                    MonthpickerCtrl.updatePosition(b2bMonthpickerPopupTemplate);
                    scope.$apply();
                }
            };
        
            var toggleCalendar = function (flag) {
                if (!scope.inline) {
                    if (flag) {
                        b2bMonthpickerPopupTemplate = angular.element($templateCache.get('b2bTemplate/monthSelector/monthSelector-popup.html'));
                        b2bMonthpickerPopupTemplate.attr('b2b-trap-focus-inside-element', 'false');
                        b2bMonthpickerPopupTemplate.attr('trigger', 'true');
                        b2bMonthpickerPopupTemplate = $compile(b2bMonthpickerPopupTemplate)(scope);
                        $document.find('body').append(b2bMonthpickerPopupTemplate);
                        b2bMonthpickerPopupTemplate.bind('keydown', escPress);
                        $timeout(function () {
                            scope.getFocus = true;
                            scope.trigger=0;
                            scope.$apply();
                            $timeout(function () {
                                scope.getFocus = false;
                                scope.$apply();
                                MonthpickerCtrl.focusNextPrev(b2bMonthpickerPopupTemplate,true);
                            }, 100);
                        });
                        scope.triggerInterval = $interval(function () {
                            //This value is updated to trigger init() function of directive on year change.
                            scope.trigger=(scope.trigger === 0 ? 1 : 0);
                        }, 200);

                    } else {
						if(b2bDatepickerPopupTemplate !== undefined) {
							b2bMonthpickerPopupTemplate.unbind('keydown', escPress);
							b2bMonthpickerPopupTemplate.remove();
						}
                        if(scope.triggerInterval)
                        {
                            $interval.cancel(scope.triggerInterval);
                            scope.triggerInterval=undefined;
                        }
                        
                        if(element.find('button').length > 0){
                            element.find('button')[0].focus();
                        }else{
                            element.find('a')[0].focus();
                        }
                        
                        scope.getFocus = false;
                    }
                }
            };

            var outsideClick = function (e) {
                var isElement = $isElement(angular.element(e.target), element, $document);
                var isb2bMonthpickerPopupTemplate = $isElement(angular.element(e.target), b2bMonthpickerPopupTemplate, $document);
                if (!(isElement || isb2bMonthpickerPopupTemplate)) {
                    scope.isOpen = false;
                    toggleCalendar(scope.isOpen);
                    scope.$apply();
                }
            };

            var escPress = function (ev) {
                if (!ev.keyCode) {
                    if (ev.which) {
                        ev.keyCode = ev.which;
                    } else if (ev.charCode) {
                        ev.keyCode = ev.charCode;
                    }
                }
                if (ev.keyCode) {
                    if (ev.keyCode === keymap.KEY.ESC) {
                        scope.isOpen = false;
                        toggleCalendar(scope.isOpen);
                        ev.preventDefault();
                        ev.stopPropagation();
                    } else if (ev.keyCode === 33) {
                        !scope.disablePrev && scope.move(-1);
                        $timeout(function () {
                            scope.getFocus = true;
                            scope.$apply();
                            $timeout(function () {
                                scope.getFocus = false;
                                scope.$apply();
                            }, 100);
                        });
                        ev.preventDefault();
                        ev.stopPropagation();
                    } else if (ev.keyCode === 34) {
                        !scope.disableNext && scope.move(1);
                        $timeout(function () {
                            scope.getFocus = true;
                            scope.$apply();
                            $timeout(function () {
                                scope.getFocus = false;
                                scope.$apply();
                            }, 100);
                        });
                        ev.preventDefault();
                        ev.stopPropagation();
                    }
                    scope.$apply();
                }
            };				
					
            $documentBind.click('isOpen', outsideClick, scope);

            scope.$on('$destroy', function () {
                if (scope.isOpen) {
                    scope.isOpen = false;
                    toggleCalendar(scope.isOpen);
                }
            });

            scope.resetTime = function (date) {
                if (typeof date === 'string') {
                    date = date + 'T12:00:00';
                }
                var dt;
                if (!isNaN(new Date(date))) {
                    dt = new Date(date);
                    if(scope.mode === 1){
                        dt = new Date(dt.getFullYear(), dt.getMonth());
                    }else{
                        dt = new Date(dt.getFullYear(), dt.getMonth(), dt.getDate());
                    }                                                            
                } else {
                    return null;
                }
                return new Date(dt.getFullYear(), dt.getMonth(), dt.getDate());
            };
            
            if (attrs.min) {
                scope.$parent.$watch($parse(attrs.min), function (value) {
                    scope.minDate = value ? scope.resetTime(value) : null;
                    refill();
                });
            }
            if (attrs.max) {
                scope.$parent.$watch($parse(attrs.max), function (value) {
                    scope.maxDate = value ? scope.resetTime(value) : null;
                    refill();
                });
            }
            if (attrs.due) {
                scope.$parent.$watch($parse(attrs.due), function (value) {
                    scope.dueDate = value ? scope.resetTime(value) : null;
                    refill();
                });
            }
            if (attrs.from) {
                scope.$parent.$watch($parse(attrs.from), function (value) {
                    scope.fromDate = value ? scope.resetTime(value) : null;
                    refill();
                });
            }

            if (attrs.legendIcon) {
                scope.$parent.$watch(attrs.legendIcon, function (value) {
                    scope.legendIcon = value ? value : null;
                    refill();
                });
            }
            if (attrs.legendMessage) {
                scope.$parent.$watch(attrs.legendMessage, function (value) {
                    scope.legendMessage = value ? value : null;
                    refill();
                });
            }
            if (attrs.ngDisabled) {
                scope.$parent.$watch(attrs.ngDisabled, function (value) {
                    scope.ngDisabled = value ? value : null;
                });
            }      
            

            // Split array into smaller arrays
            function split(arr, size) {
                var arrays = [];
                while (arr.length > 0) {
                    arrays.push(arr.splice(0, size));
                }
                return arrays;
            }
            
            var moveMonth = function(selectedDate, direction) {
                var step = MonthpickerCtrl.modes[scope.mode].step;
                selectedDate.setDate(1);
                selectedDate.setMonth(selectedDate.getMonth() + direction * (step.months || 0));
                selectedDate.setFullYear(selectedDate.getFullYear() + direction * (step.years || 0));

                return selectedDate;
            };            

            function refill(date) {
                if (angular.isDate(date) && !isNaN(date)) {
                    selected = new Date(date);
                } else {
                    if (!selected) {
                        selected = new Date();
                    }
                }

                if (selected) {                    
                    var selectedCalendar;
                    if(scope.mode === 1){
                        if(!angular.isDate(selected))
                           {                           
                                selected = new Date();
                           }
                        selectedCalendar = moveMonth(angular.copy(selected), -1);
                    } else {
                        selectedCalendar = angular.copy(selected);
                    }
                    
                    var currentMode = MonthpickerCtrl.modes[mode],
                        data = currentMode.getVisibleDates(selected);

                    scope.rows = split(data.objects, currentMode.split);
            
                    var flag=false;
                    var startFlag=false;
                    var firstSelected = false;
                    for(var i=0; i<scope.rows.length; i++)
                    {
                        for(var j=0; j<scope.rows[i].length; j++)
                        {
                            if(!scope.rows[i][j].disabled && !firstSelected)
                            {
                                firstSelected=true;
                                var firstDay = scope.rows[i][j];
                            }

                            if(scope.rows[i][j].selected)
                            {
                                flag=true;
                                break;
                            }                  
                        }
                        if(flag)
                        {
                            break;
                        }
                    }
                    if(!flag && firstSelected)
                    {
                       firstDay.firstFocus=true;
                    }

                    scope.labels = data.labels || [];
                    scope.title = data.title;                    
                }
            }

            scope.select = function (date,$event) {
                var dt = new Date(date.getFullYear(), date.getMonth(), date.getDate());
                scope.currentDate = dt;
                if (!scope.onSelectClose || (scope.onSelectClose && scope.onSelectClose({
                        date: dt
                    }) !== false)) {
                    if (angular.isNumber(scope.collapseWait)) {
                        $timeout(function () {
                            scope.isOpen = false;
                            toggleCalendar(scope.isOpen);
                        }, scope.collapseWait);
                    } else {
                        scope.isOpen = false;
                        toggleCalendar(scope.isOpen);
                    }
                }
            };

            scope.move = function (direction,$event) {
                var step = MonthpickerCtrl.modes[mode].step;
                selected.setDate(1);
                selected.setMonth(selected.getMonth() + direction * (step.months || 0));
                selected.setFullYear(selected.getFullYear() + direction * (step.years || 0));
                refill();
                scope.getFocus = true;
                $timeout(function () {
                    if (attrs.inline === 'true') {
                        MonthpickerCtrl.focusNextPrev(element,false); 
                    }else{
                        MonthpickerCtrl.focusNextPrev(b2bMonthpickerPopupTemplate,false);
                    }
                },100);
                $event.preventDefault();
                $event.stopPropagation();
            };

            scope.$watch('currentDate', function (value) {
                if (angular.isDefined(value) && value !== null) {
                    refill(value);
                } else {
                    refill();
                }
                ngModel.$setViewValue(value);
            });

            ngModel.$render = function () {
                scope.currentDate = ngModel.$viewValue;
            };

            var stringToDate = function (value) {
                if (!isNaN(new Date(value))) {
                    value = new Date(value);
                }
                return value;
            };
            ngModel.$formatters.unshift(stringToDate);
        }
    };
}])

.directive('b2bMonthpicker', ['$compile', '$log', 'b2bMonthpickerConfig', 'b2bMonthpickerService', function ($compile, $log, b2bMonthpickerConfig, b2bMonthpickerService) {
    return {
        restrict: 'A',
        scope: {
            disableDates: '&',
            onSelectClose: '&'
        },
        require: 'ngModel',
        controller: ['$scope', '$element', '$attrs', function (scope, elem, attr) {
            var dateFormatString = angular.isDefined(attr.dateFormat) ? scope.$parent.$eval(attr.dateFormat) : b2bMonthpickerConfig.dateFormat;
            var helperText = angular.isDefined(attr.helperText) ? scope.$parent.$eval(attr.helperText) : b2bMonthpickerConfig.helperText; 
            helperText = helperText.replace('$date', '{{dt | date : \'' + dateFormatString + '\'}}');

            var descriptionText = angular.isDefined(attr.descriptionText) ? scope.$parent.$eval(attr.descriptionText) : b2bMonthpickerConfig.descriptionText;  


            var inline = false;
            if (elem.prop('nodeName') !== 'INPUT' && elem.prop('nodeName') !== 'A') {
                inline = true;
            }

            var selectedDateMessage = "";
            
            if (elem.prop('nodeName') !== 'A'){
                selectedDateMessage = '<button type="button" class="span12 faux-input" ng-disabled="ngDisabled" aria-describedby="monthpicker-description'+scope.$id+'"><span class="hidden-spoken" aria-live="assertive" aria-atomic="false">' + helperText + '</span></button>';    
                elem.attr('tabindex', '-1'); 
                elem.attr('aria-hidden', 'true');  
                elem.attr('readonly', 'true'); 
            }else{
                selectedDateMessage = ''
                elem.attr('aria-label', helperText);
            }
            
            var descriptionTextSpan = '<span class="offscreen-text" id="monthpicker-description'+scope.$id+'">'+descriptionText+'</span>';
            elem.removeAttr('b2b-Monthpicker');
            elem.removeAttr('ng-model');
            elem.removeAttr('ng-disabled');
            elem.addClass('Monthpicker-input');
            elem.attr('ng-model', 'dt');
            elem.attr('aria-describedby', 'monthpicker-description'+scope.$id);
            
            
            
            elem.attr('ng-disabled', 'ngDisabled');
            elem.attr('b2b-format-date', dateFormatString);

            var wrapperElement = angular.element('<div></div>');
            wrapperElement.attr('b2b-Monthpicker-popup', '');
            wrapperElement.attr('ng-model', 'dt');
            if (inline) {
                wrapperElement.attr('inline', inline);
            }
            if (elem.prop('nodeName') === 'A'){
                wrapperElement.attr('link', true);
            }
            b2bMonthpickerService.setAttributes(attr, wrapperElement);
            b2bMonthpickerService.bindScope(attr, scope);

            wrapperElement.html('');
            wrapperElement.append(selectedDateMessage);
            wrapperElement.append('');
            wrapperElement.append(descriptionTextSpan);
            wrapperElement.append('');
            wrapperElement.append(elem.prop('outerHTML'));

            var elm = wrapperElement.prop('outerHTML');
            elm = $compile(elm)(scope);
            elem.replaceWith(elm);
        }],
        link: function (scope, elem, attr, ctrl) {
            if (!ctrl) {
                $log.error("ng-model is required.");
                return; // do nothing if no ng-model
            }
            
            scope.$watch('dt', function (value) {
                ctrl.$setViewValue(value);
            });
            ctrl.$render = function () {
                scope.dt = ctrl.$viewValue;
            };
        }
    };
}])

.directive('b2bMonthpickerGroup', [function () {
    return {
        restrict: 'EA',
        controller: ['$scope', '$element', '$attrs', function (scope, elem, attr) {
            this.$$headers = [];
            this.$$footers = [];
            this.registerMonthpickerScope = function (MonthpickerScope) {
                MonthpickerScope.headers = this.$$headers;
                MonthpickerScope.footers = this.$$footers;
            };
        }],
        link: function (scope, elem, attr, ctrl) {}
    };
}])

.directive('b2bFormatDate', ['dateFilter', function (dateFilter) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, elem, attr, ctrl) {
            var b2bFormatDate = "";
            attr.$observe('b2bFormatDate', function (value) {
                b2bFormatDate = value;
            });
            var dateToString = function (value) {
                if (!isNaN(new Date(value))) {
                    return dateFilter(new Date(value), b2bFormatDate);
                }
                return value;
            };
            ctrl.$formatters.unshift(dateToString);
        }
    };
}])

.directive('b2bMonthpickerHeader', [function () {
    return {
        restrict: 'EA',
        require: '^b2bMonthpickerGroup',
        transclude: true,
        replace: true,
        template: '',
        compile: function (elem, attr, transclude) {
            return function link(scope, elem, attr, ctrl) {
                if (ctrl) {
                    ctrl.$$headers.push(transclude(scope, function () {}));
                }
                elem.remove();
            };
        }
    };
}])

.directive('b2bMonthpickerFooter', [function () {
    return {
        restrict: 'EA',
        require: '^b2bMonthpickerGroup',
        transclude: true,
        replace: true,
        template: '',
        compile: function (elem, attr, transclude) {
            return function link(scope, elem, attr, ctrl) {
                if (ctrl) {
                    ctrl.$$footers.push(transclude(scope, function () {}));
                }
                elem.remove();
            };
        }
    };
}]);
/**
 * @ngdoc directive
 * @name Navigation.att:multiLevelNavigation
 *
 * @description
 *  <file src="src/multiLevelNavigation/docs/readme.md" />
 *
 * @usage
 *       <div class="b2b-ml-nav">
 *          <ul role="tree">
 *             <li aria-label="{{child.name}}" tabindex="-1" b2b-ml-nav="{{child.type}}" role="treeitem" ng-repeat="child in treeStructure">
 *                  <a tabindex="-1" href="javascript:void(0);" title="{{child.name}}">{{child.name}}<span><i class="{{child.type=='endNode'?'icon-circle':'icon-collapsed'}}"></i></span></a>
 *                      <!-- Below UL tag is RECURSIVE to generate n-childs -->
 *                      <ul role="group" ng-if="child.child">
 *                          <li aria-label="{{child.name}}" b2b-ml-nav="{{child.type}}" tabindex="-1" role="treeitem" ng-repeat="child in child.child">
 *                          <a tabindex="-1" href="javascript:void(0);" title="{{child.name}}">{{child.name}}<span><i class="{{child.type=='endNode'?'icon-circle':'icon-collapsed'}}"></i></span></a>
 *                               <!-- RECURSIVE UL tag goes here -->
 *                          </li>
 *                      </ul>
 *             </li>
 *           </ul>
 *        </div>
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/multiLevelNavigation/docs/demo.html" />
            <file src="src/multiLevelNavigation/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.multiLevelNavigation', ['b2b.att.utilities'])
    //directive b2bMlNav Test coverage 100% on 5/13
    .directive('b2bMlNav', ['keymap', function (keymap) {
        return {
            restrict: 'EA',
            link: function (scope, element) {
                var rootE, parentE, upE, downE, lastE, homeE, endE;
                //default root tree element tabindex set zero
                if (element.parent().parent().hasClass('b2b-ml-nav') && (element[0].previousElementSibling === null)) {
                    element.attr('tabindex', 0);
                }
                //check root via class
                var isRoot = function (elem) {
                        if (elem.parent().parent().eq(0).hasClass('b2b-ml-nav')) {
                            return true;
                        } else {
                            return false;
                        }

                    }
                    //for any expandable tree item on click
                var toggleState = function (e) {
                                    
                    if (angular.element(e.target).attr("b2b-ml-nav") !== "endNode") {
                        var eLink = element.find('a').eq(0);
                        if (eLink.hasClass('active')) {
                            eLink.removeClass('active');
                            eLink.parent().attr("aria-expanded", "false");
                            eLink.find('i').eq(0).removeClass('icon-expanded');
                            eLink.find('i').eq(0).addClass('icon-collapsed');
                        } else {
                            eLink.addClass('active');
                            eLink.parent().attr("aria-expanded", "true");
                            eLink.find('i').eq(0).removeClass('icon-collapsed');
                            eLink.find('i').eq(0).addClass('icon-expanded');
                        }
                    }
                };
                //function finds the main root-item from particular tree-group
                var findRoot = function (elem) {
                    if (isRoot(elem)) {
                        rootE = elem;
                        return;
                    }
                    if (elem.attr("b2b-ml-nav") === "middleNode" || elem.attr("b2b-ml-nav") === "endNode") {
                        parentE = elem.parent().parent();
                    } else {
                        parentE = elem;
                    }
                    if (parentE.attr("b2b-ml-nav") === "rootNode") {
                        rootE = parentE;
                    } else {
                        findRoot(parentE);
                    }
                };
                //finds the last visible node of the previous tree-group
                var findPreActive = function (elem) {
                    if (!(elem.hasClass("active"))) {
                        return;
                    } else {
                        var childElems = angular.element(elem[0].nextElementSibling.children);
                        lastE = angular.element(childElems[childElems.length - 1]);
                        if (lastE.attr("b2b-ml-nav") === "middleNode" && lastE.find('a').eq(0).hasClass('active')) {
                            findPreActive(lastE.find('a').eq(0));
                        }
                        upE = lastE;
                    }
                };
                //find above visible link
                var findUp = function (elem) {
                    if (elem[0].previousElementSibling !== null) {
                        upE = angular.element(elem[0].previousElementSibling);
                    } else {
                        upE = elem.parent().parent();
                    }
                    if (isRoot(elem) || (upE.attr('b2b-ml-nav') === "middleNode" && upE[0] !== elem.parent().parent()[0])) {
                        findPreActive(upE.find('a').eq(0)); 
                    }
                };
                //find below visible link
                var findDown = function (elem) {
                    if (elem.hasClass('active')) {
                        downE = elem.next().find('li').eq(0);
                    } else {
                        if (elem.parent().next().length !== 0) {
                            downE = elem.parent().next().eq(0);
                        } else {
                            if (elem.parent().parent().parent().next().length !== 0) {
                                downE = elem.parent().parent().parent().next().eq(0);
                                return;
                            }
                            downE = elem.parent().eq(0);
                        }
                    }
                };
                //finds last root-group element of the tree
                var findEnd = function (elem) {
                    findRoot(elem);
                    endE = angular.element(rootE.parent()[0].children[rootE.parent()[0].children.length - 1]);
                };
                //finds first root element of tree
                var findHome = function (elem) {
                    findRoot(elem);
                    homeE = angular.element(rootE.parent()[0].children[0]);
                };
                element.bind('click', function (e) {
                    if(element.attr("b2b-ml-nav") !== "endNode") { 
                        toggleState(e); 
                    }
                    if (rootE==undefined){
                        findRoot(element);
                    }
                    var currSelected = rootE.parent()[0].querySelector('.selected');
                    if(currSelected){
                        angular.element(currSelected).removeClass('selected');
                    }                    
                    element.find('a').eq(0).addClass('selected');
                    e.stopPropagation();
                });
                element.bind('focus', function (e) {
                    if(element.attr("b2b-ml-nav") !== "endNode") {
                        if(element.find('a').eq(0).hasClass('active')) {
                            element.attr("aria-expanded", true);
                        }
                        else {
                            element.attr("aria-expanded", false);
                        }
                        
                    }
                })
                //Keyboard functionality approach:
                //find keycode
                //set set tabindex -1 on the current focus element
                //find the next element to be focussed, set tabindex 0 and throw focus
                element.bind('keydown', function (evt) {
                    switch (evt.keyCode) {
                    case keymap.KEY.ENTER:
                    case keymap.KEY.SPACE:
                        element.triggerHandler('click');
                        evt.stopPropagation();
                        evt.preventDefault();
                        break;
                    case keymap.KEY.END:
                        evt.preventDefault();
                        element.attr('tabindex', -1);
                        findEnd(element);
                        endE.eq(0).attr('tabindex', 0);
                        endE[0].focus();
                        evt.stopPropagation();
                        break;
                    case keymap.KEY.HOME:
                        evt.preventDefault();
                        element.attr('tabindex', -1);
                        findHome(element);
                        homeE.eq(0).attr('tabindex', 0);
                        homeE[0].focus();
                        evt.stopPropagation();
                        break;
                    case keymap.KEY.LEFT:
                        evt.preventDefault();
                        if (!isRoot(element)) {
                            element.attr('tabindex', -1);
                            parentE = element.parent().parent();
                            parentE.eq(0).attr('tabindex', 0);
                            parentE[0].focus();
                            parentE.eq(0).triggerHandler('click');
                        } else {
                            if (element.find('a').eq(0).hasClass('active')) {
                                element.triggerHandler('click');
                            }
                        }
                        evt.stopPropagation();
                        break;
                    case keymap.KEY.UP:
                        evt.preventDefault();
                        if (!(isRoot(element) && element[0].previousElementSibling === null)) {
                            element.attr('tabindex', -1);
                            findUp(element);
                            upE.eq(0).attr('tabindex', 0);
                            upE[0].focus();
                        }
                        evt.stopPropagation();
                        break;
                    case keymap.KEY.RIGHT:
                        evt.preventDefault();
                        if (element.attr("b2b-ml-nav") !== "endNode") {
                            if (!element.find('a').eq(0).hasClass('active')) {
                                element.triggerHandler('click');
                            }
                            element.attr('tabindex', -1);
                            findDown(element.find('a').eq(0));
                            downE.eq(0).attr('tabindex', 0);
                            downE[0].focus();
                        }
                        evt.stopPropagation();
                        break;
                    case keymap.KEY.DOWN:
                        evt.preventDefault();
                        element.attr('tabindex', -1);
                        if (!(element.attr("b2b-ml-nav") === "middleNode" && element.find('a').eq(0).hasClass('active')) && (element.next().length === 0)) {
                            if(element.parent().parent().attr("b2b-ml-nav") !== "rootNode" && element.parent().parent()[0].nextElementSibling !== null)
                            {
                                findDown(element.find('a').eq(0));
                                downE.eq(0).attr('tabindex', 0);
                                downE[0].focus();
                                evt.stopPropagation();
                                break;
                            }
                            findRoot(element);
                            if (!(rootE.next().length === 0)) {
                                rootE.next().eq(0).attr('tabindex', 0);
                                rootE.next()[0].focus();
                            } else {
                                rootE.eq(0).attr('tabindex', 0);
                                rootE[0].focus();
                            }
                            evt.stopPropagation();
                            break;
                        }
                        findDown(element.find('a').eq(0));
                        downE.eq(0).attr('tabindex', 0);
                        downE[0].focus();
                        evt.stopPropagation();
                        break;
                    default:
                        break;
                    }
                });
            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Tabs, tables & accordions.att:multipurposeExpander
 *
 * @description
 *  <file src="src/multipurposeExpander/docs/readme.md" />
 *
 * @usage
 * <!--With Close Other -->
 * <b2b-expander-group close-others="true">
 *  <b2b-expanders class="mpc-expanders" is-open="testmpc">            
 *      <b2b-expander-heading ng-class=" { 'b2b-toggle-header-active': !testmpc, 'b2b-toggle-header-inactive': testmpc } ">Heading content goes here</b2b-expander-heading>               
 *      <b2b-expander-body>
            <p>body content goes here</p>
        </b2b-expander-body>
 *  </b2b-expanders>
 *  </b2b-expander-group>
 *  
 * <!-- Without Close Other -->
 *  <b2b-expanders class="mpc-expanders" is-open="testmpc2">            
 *      <b2b-expander-heading ng-class=" { 'b2b-toggle-header-active': !testmpc2, 'b2b-toggle-header-inactive': testmpc2 } ">Heading content goes here</b2b-expander-heading>               
 *      <b2b-expander-body>
            <p>body content goes here</p>
        </b2b-expander-body>
 *  </b2b-expanders>
 *  
 * @example
 *  <section id="code">
        <example module="b2b.att.multipurposeExpander">
            <file src="src/multipurposeExpander/docs/demo.html" />
            <file src="src/multipurposeExpander/docs/demo.js" />
        </example>
    </section>
 *
 */

angular.module('b2b.att.multipurposeExpander', ['b2b.att', 'b2b.att.collapse'])
.directive('b2bExpanderGroup', function () {
    return {
        restrict: 'EA',
        transclude: true,
        template: "<ng-transclude></ng-transclude>",
        controller:['$scope','$attrs', function($scope,$attrs){
            this.groups = [];
            this.index = -1;            
            this.scope = $scope;
            
            this.addGroup = function (groupScope) {
                var that = this;
                groupScope.index = this.groups.length;
                this.groups.push(groupScope);
                if(this.groups.length > 0){
                    this.index = 0;
                }
                groupScope.$on('$destroy', function () {
                that.removeGroup(groupScope);
            });
            };

            this.closeOthers = function (openGroup) {
                var closeOthers = angular.isDefined($attrs.closeOthers);
                if (closeOthers && !$scope.forceExpand) {
                    angular.forEach(this.groups, function (group) {
                        if (group !== openGroup) {
                            group.isOpen = false;
                        }
                    });
                }
                if (this.groups.indexOf(openGroup) === (this.groups.length - 1) && $scope.forceExpand) {
                    $scope.forceExpand = false;
                }
            };
            this.removeGroup = function (group) {
            var index = this.groups.indexOf(group);
            if (index !== -1) {
                this.groups.splice(this.groups.indexOf(group), 1);
            }
        };
        }]
       
    };
    
})
.directive('b2bExpanders', function () {
    return{
        restrict: 'EA',
        replace: true,
        require:['b2bExpanders','?^b2bExpanderGroup'],
        transclude: true,
        scope:{isOpen:'=?'},
        template: "<div ng-transclude></div>",
        controller: ['$scope', function ($scope){
                var bodyScope = null;
                var expanderScope = null;
                this.isOpened = function(){                
                    if($scope.isOpen)
                    {
                        return  true;
                    }else
                    {
                        return false;
                    }
                };                
                this.setScope = function (scope) {
                    bodyScope = scope; 
                    bodyScope.isOpen = $scope.isOpen;                   
                };                
                this.setExpanderScope = function (scope) {
                    expanderScope = scope;                                   
                };
                this.toggle = function () {
                    $scope.isOpen = bodyScope.isOpen = !bodyScope.isOpen;                    
                    return bodyScope.isOpen;
                    
                };
                this.watchToggle = function(io){
                    if(bodyScope !== null && angular.isDefined(bodyScope) && expanderScope !== null && angular.isDefined(expanderScope)){ 
                        bodyScope.isOpen = io;
                        expanderScope.updateIcons(io);
                    }
                };  
            }],
        link: function (scope, elem, attr, myCtrl)
        {
            //scope.isOpen = false; 
            if(myCtrl[1]){
                myCtrl[1].addGroup(scope);
            }
            scope.$watch('isOpen', function(val){                               
                myCtrl[0].watchToggle(scope.isOpen);
                if(val && myCtrl[1]){
                    myCtrl[1].closeOthers(scope);
                }
            });            
        }
    };
})

.directive('b2bExpanderHeading', function () {
    return{
        require: "^b2bExpanders",
        restrict: 'EA',
        replace: true,
        transclude: true,
        scope: true,
        template: "<div style='padding:10px 10px 10px 0 !important' ng-transclude></div>"
    };
})

.directive('b2bExpanderBody', function () {
    return{
        restrict: 'EA',
        require: "^b2bExpanders",
        replace: true,
        transclude: true,
        scope: {},
        template: "<div b2b-collapse='!isOpen' ><div ng-transclude></div></div>",
        link: function (scope, elem, attr, myCtrl) {
            scope.isOpen = false;
            myCtrl.setScope(scope);
        }
    };
})

.directive('b2bExpanderToggle', function () {
    return{
        restrict: 'EA',
        require: "^b2bExpanders",
        scope: {
            expandIcon: '@',
            collapseIcon: '@'
        },
        
        link: function (scope, element, attr, myCtrl)
        {
            myCtrl.setExpanderScope(scope);
            var isOpen = myCtrl.isOpened();   

            scope.setIcon = function () {
                element.attr("role", "button");

                if (scope.expandIcon && scope.collapseIcon)
                {
                    if (isOpen) {
                        element.removeClass(scope.expandIcon);
                        element.addClass(scope.collapseIcon);

                        element.attr("aria-expanded", "true");
                    }
                    else {
                        element.removeClass(scope.collapseIcon);
                        element.addClass(scope.expandIcon);

                        element.attr("aria-expanded", "false");
                    }
                }								
            };
            
            element.bind('click', function (){
                scope.toggleit();
            });
            scope.updateIcons = function(nStat){
                isOpen = nStat;
                scope.setIcon();                
            };
            scope.toggleit = function (){
                isOpen = myCtrl.toggle();
                scope.setIcon();
                scope.$apply();
            };                    
            scope.setIcon();
        }
    };
});
/**
 * @ngdoc directive
 * @name Messages, modals & alerts.att:notesMessagesAndErrors
 *
 * @description
 *  <file src="src/notesMessagesAndErrors/docs/readme.md" />
 *
 * @usage
 *  See Demo
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/notesMessagesAndErrors/docs/demo.html" />
            <file src="src/notesMessagesAndErrors/docs/demo.js" />
       </example>
        </section>
 *
 */
angular.module('b2b.att.notesMessagesAndErrors', []);
/** 
 * @ngdoc directive 
 * @name Template.att:Notification Card
 * 
 * @description 
 *  <file src="src/notificationCardTemplate/docs/readme.md" /> 
 * 
 * @example 
 *  <section id="code"> 
        <b>HTML + AngularJS</b> 
        <example module="b2b.att"> 
            <file src="src/notificationCardTemplate/docs/demo.html" /> 
            <file src="src/notificationCardTemplate/docs/demo.js" /> 
       </example> 
    </section>    
 * 
 */
angular.module('b2b.att.notificationCardTemplate', [])
  
/** 
 * @ngdoc directive 
 * @name Template.att:Order Confirmation Template
 * 
 * @description 
 *  <file src="src/orderConfirmationTemplate/docs/readme.md" /> 
 * 
 * @example 
 *  <section id="code"> 
        <b>HTML + AngularJS</b> 
        <example module="b2b.att"> 
            <file src="src/orderConfirmationTemplate/docs/demo.html" /> 
            <file src="src/orderConfirmationTemplate/docs/demo.js" /> 
       </example> 
    </section>    
 * 
 */
angular.module('b2b.att.orderConfirmationTemplate', []);
  
/**
 * @ngdoc directive
 * @name Navigation.att:pagination
 *
 * @description
 *  <file src="src/pagination/docs/readme.md" />
 * @param {int} total-pages - Total # of pages, set in your controller $scope
 * @param {int} current-page - Current selected page, set in your controller $scope
 * @param {function} click-handler - Handler function on click of page number, defined in your controller $scope
 * @param {string} input-id - _UNIQUE ID_ __MUST__ be provided for 508 compliance, set in your HTML as static text
 * @param {string} input-class - optional class that can be given to use for the go to page container
 *
 * @usage
 *   <div b2b-pagination="" input-id="goto-page-2" total-pages="totalPages1" current-page="currentPage1" click-handler="customHandler"></div> 
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/pagination/docs/demo.html" />
            <file src="src/pagination/docs/demo.js" />
       </example>
        </section>
 *
 */
angular.module('b2b.att.pagination', ['b2b.att.utilities', 'ngTouch'])
    .directive('b2bPagination', ['b2bUserAgent', 'keymap', '$window', '$timeout', function (b2bUserAgent, keymap, $window, $timeout) {
        return {
            restrict: 'A',
            scope: {
                totalPages: '=',
                currentPage: '=',
                clickHandler: '=?',
                inputId: '=',
                isDroppable: '=?'
            },
            replace: true,
            templateUrl: 'b2bTemplate/pagination/b2b-pagination.html',
            link: function (scope, elem, attr) {
                scope.isMobile = b2bUserAgent.isMobile();
                scope.notMobile = b2bUserAgent.notMobile();
                scope.focusedPage;
                scope.meanVal = 3;
                scope.inputClass = attr.inputClass;
                scope.droppableAttribute = scope.isDroppable ? true : false;
                scope.$watch('totalPages', function (value) {
                    if (angular.isDefined(value) && value !== null) {
                        scope.pages = [];
                        if (value < 1) {
                            scope.totalPages = 1;
                            return;
                        }
                        if (value <= 10) {
                            for (var i = 1; i <= value; i++) {
                                scope.pages.push(i);
                            }
                        } else if (value > 10) {
                            var midVal = Math.ceil(value / 2);
                            scope.pages = [midVal - 2, midVal - 1, midVal, midVal + 1, midVal + 2];
                        }
                        if(scope.currentPage === undefined || scope.currentPage === 1)
                        {
                            currentPageChanged(1);
                        }
                    }
                });
                scope.$watch('currentPage', function (value) {
                    currentPageChanged(value);
                    callbackHandler(value);
                });
                var callbackHandler = function (num) {
                    if (angular.isFunction(scope.clickHandler)) {
                        scope.clickHandler(num);
                    }
                };
                var getBoundary = function(value){
                    if ( value < 100 ) {
                        return 5;
                    } else if ( 100 <= value && value < 1000 ) {
                        return 4;
                    } else if ( 1000 <= value ) {
                        return 3;
                    } else {
                        return 5; // error
                    }
                };
                function currentPageChanged(value) {
                    if (angular.isDefined(value) && value !== null) {
                        if (!value || value < 1) {
                            value = 1;
                        }
                        if (value > scope.totalPages) {
                            value = scope.totalPages;
                        }
                        if (scope.currentPage !== value) {
                            scope.currentPage = value;
                            callbackHandler(scope.currentPage);
                        }
                        if (scope.totalPages > 10) {
                            var val = parseInt(value);
                            scope.boundary = getBoundary(val);
                            if (val <= 6) { // Left (first) section
                                scope.pages = [1, 2, 3, 4, 5, 6, 7];
                            } else if ( val <= (scope.totalPages - scope.boundary) ) { // Middle section
                                if ( 7 <= val && val < 9 ) {
                                    if(scope.totalPages < 100) {
                                        scope.pages = [val - 3, val - 2, val - 1, val, val + 1, val + 2];
                                    } else if(scope.totalPages < 1000) {
                                        scope.pages = [val - 2, val - 1, val, val + 1, val + 2];
                                    } else if(scope.totalPages < 1000) {
                                        scope.pages = [val - 2, val - 1, val, val + 1, val + 2];
                                    }
                                } else if ( 9 <= val && val < 100 ) {
                                    scope.pages = [val - 3, val - 2, val - 1, val, val + 1, val + 2];
                                } else if ( 100 <= val && val < 1000 ) {
                                    scope.pages = [val - 2, val - 1, val, val + 1];
                                } else if ( 1000 <= val ) {
                                    scope.pages = [val - 1, val, val + 1];
                                }
                            } else if ( (scope.totalPages - scope.boundary) < val ) { // Right (last) section
                                if ( val < 100 ) {
                                    scope.pages = [scope.totalPages - 5, scope.totalPages - 4, scope.totalPages - 3, scope.totalPages - 2, scope.totalPages - 1, scope.totalPages];
                                } else if ( 100 <= val && val < 1000 ) {
                                    scope.pages = [scope.totalPages - 4, scope.totalPages - 3, scope.totalPages - 2, scope.totalPages - 1, scope.totalPages];
                                } else if ( 1000 <= val ) {
                                    scope.pages = [scope.totalPages - 3, scope.totalPages - 2, scope.totalPages - 1, scope.totalPages];
                                }
                            }
                        }
                        if (scope.isMobile) {
                            var inWidth = $window.innerWidth;
                            var viewLimit = 7;
                            if (inWidth <= 400) {
                                viewLimit = 7;
                            } else if (inWidth > 400 && inWidth < 500) {
                                viewLimit = 9;
                            } else if (inWidth >= 500 && inWidth < 600) {
                                viewLimit = 11;
                            } else if (inWidth >= 600 && inWidth < 700) {
                                viewLimit = 13;
                            } else if (inWidth >= 700 && inWidth < 800) {
                                viewLimit = 15;
                            }

                            var val = parseInt(value);

                            scope.meanVal = Math.floor(viewLimit / 2);
                            var lowerLimit = (val - scope.meanVal) < 1 ? 1 : val - scope.meanVal;
                            var upperLimit = (lowerLimit + viewLimit - 1) > scope.totalPages ? scope.totalPages : lowerLimit + viewLimit - 1;
                            scope.pages = [];
                            for (var i = lowerLimit; i <= upperLimit; i++) {
                                scope.pages.push(i);
                            }
                        }
                    }
                }
                scope.gotoKeyClick = function (keyEvent) {
                  if (keyEvent.which === keymap.KEY.ENTER) {
                    scope.gotoBtnClick()
                  }
                }
                scope.gotoBtnClick = function () {
                    currentPageChanged(parseInt(scope.gotoPage)); 
                    callbackHandler(scope.currentPage);
                    var qResult = elem[0].querySelector('button');
                    angular.element(qResult).attr('disabled','true');
                    $timeout(function(){
                        elem[0].querySelector('.b2b-pager__item--active').focus();
                    }, 50); 
                    scope.gotoPage = null;               
                }
                scope.onfocusIn = function(evt)
                {
                    var qResult = elem[0].querySelector('button');
                    angular.element(qResult).removeAttr('disabled');
                }
                scope.onfocusOut = function(evt)
                {
                    if(evt.target.value === "")
                    {
                        var qResult = elem[0].querySelector('button');
                        angular.element(qResult).attr('disabled','true');
                    }                    
                }
                scope.next = function (event) {
                    if (event != undefined) {
                        event.preventDefault();
                    }
                    if (scope.currentPage < scope.totalPages) {
                        scope.currentPage += 1;
                        callbackHandler(scope.currentPage);
                    }
                };
                scope.prev = function (event) {
                    if (event != undefined) {
                        event.preventDefault();
                    }
                    if (scope.currentPage > 1) {
                        scope.currentPage -= 1;
                        callbackHandler(scope.currentPage);
                    }
                };
                scope.selectPage = function (value, event) {
                    event.preventDefault();
                    scope.currentPage = value;
                    scope.focusedPage = value;
                    callbackHandler(scope.currentPage);
                };
                scope.checkSelectedPage = function (value) {
                    if (scope.currentPage === value) {
                        return true;
                    }
                    return false;
                };
                scope.isFocused = function (page) {
                    return scope.focusedPage === page;
                };
            }
        };
    }]);

/**
 * @ngdoc directive
 * @name Navigation.att:paneSelector
 *
 * @description
 *  <file src="src/paneSelector/docs/readme.md" />
 *
 * @usage
 *  Please refer demo.html tab in Example section below.
 *
 * @example
    <section id="code">
        <b>HTML + AngularJS</b>
        <example module="b2b.att">
            <file src="src/paneSelector/docs/demo.html" />
            <file src="src/paneSelector/docs/demo.js" />
        </example>
    </section>
 */

angular.module('b2b.att.paneSelector', ['b2b.att.tabs', 'b2b.att.utilities'])

.filter('paneSelectorSelectedItemsFilter', [function () {
    return function (listOfItemsArray) {

        if (!listOfItemsArray) {
            listOfItemsArray = [];
        }

        var returnArray = [];

        for (var i = 0; i < listOfItemsArray.length; i++) {
            if (listOfItemsArray[i].isSelected) {
                returnArray.push(listOfItemsArray[i]);
            }
        }

        return returnArray;
    };
}])

.filter('paneSelectorFetchChildItemsFilter', [function () {
    return function (listOfItemsArray) {

        if (!listOfItemsArray) {
            listOfItemsArray = [];
        }

        var returnArray = [];

        for (var i = 0; i < listOfItemsArray.length; i++) {
            for (var j = 0; j < listOfItemsArray[i].childItems.length; j++) {
                returnArray.push(listOfItemsArray[i].childItems[j]);
            }
        }

        return returnArray;
    };
}])

.directive('b2bPaneSelector', [function () {
    return {
        restrict: 'AE',
        replace: true,
        templateUrl: 'b2bTemplate/paneSelector/paneSelector.html',
        transclude: true,
        scope: {}
    };
}])

.directive('b2bPaneSelectorPane', [ function () {
    return {
        restrict: 'AE',
        replace: true,
        templateUrl: 'b2bTemplate/paneSelector/paneSelectorPane.html',
        transclude: true,
        scope: {}
    };
}])

.directive('b2bTabVertical', ['$timeout', 'keymap', function ($timeout, keymap) {
    return {
        restrict: 'A',
        require: '^b2bTab',
        link: function (scope, element, attr, b2bTabCtrl) {

            if (!b2bTabCtrl) {
                return;
            }

            // retreive the isolateScope
            var iScope = angular.element(element).isolateScope();

            $timeout(function () {
                angular.element(element[0].querySelector('a')).unbind('keydown');
                angular.element(element[0].querySelector('a')).bind('keydown', function (evt) {

                    if (!(evt.keyCode)) {
                        evt.keyCode = evt.which;
                    }

                    switch (evt.keyCode) {
                        case keymap.KEY.DOWN:
                            evt.preventDefault();
                            iScope.nextKey();
                            break;

                        case keymap.KEY.UP:
                            evt.preventDefault();
                            iScope.previousKey();
                            break;

                        default:;
                    }
                });
            });
        }
    };
}]);
/**
 * @ngdoc directive
 * @name Forms.att:phoneNumberInput
 *
 * @description
 *  <file src="src/phoneNumberInput/docs/readme.md" />
 *
 * @usage
<form name="userForm1">
    <div class="form-row" ng-class="{'error':!userForm1.text.$valid && userForm1.text.$dirty}">
        <label>Phone Mask<span style="color:red">*</span>: (npa) nxx-line &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Model Value: {{mask.text}}</label>
        <div>
            <input b2b-phone-mask="phoneMask" name="text" ng-model="mask.text" type="text" placeholder="e.g. (123) 456-7890" title="Phone Number" class="b2b-phone-mask-input" required />
            <div ng-messages="userForm1.text.$error" class="error-msg" aria-live="polite" aria-atomic="true">
                <span ng-message="required" role="alert">This field is mandatory!</span>
                <span ng-message="invalidPhoneNumber" role="alert">Please enter valid phone number!</span>
                <span ng-message="mask" role="alert">Please enter valid phone number!</span>
            </div>
        </div>
    </div>
</form>
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/phoneNumberInput/docs/demo.html" />
            <file src="src/phoneNumberInput/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.phoneNumberInput', ['ngMessages', 'b2b.att.utilities'])
    .constant("CoreFormsUiConfig", {
        phoneMask: '(___) ___-____',
        phoneMaskDot: '___.___.____',
        phoneMaskHyphen: '___-___-____'
    })
    .directive('b2bPhoneMask', ['$parse', 'CoreFormsUiConfig', 'keymap', 'b2bUserAgent', function ($parse, CoreFormsUiConfig, keymap, b2bUserAgent) {
        return {
            require: 'ngModel',
            scope: {
                ngModel: '='
            },
            link: function (scope, iElement, iAttrs, ctrl) {
                
                var mask = '';
                var validPhoneNumber = false;
                var currentKey = '';
                if (b2bUserAgent.isMobile()) {
                    mask = "__________";
                } else {
                    switch (iAttrs.b2bPhoneMask) {
                    case "phoneMask":
                        mask = CoreFormsUiConfig.phoneMask;
                        break;
                    case "phoneMaskDot":
                        mask = CoreFormsUiConfig.phoneMaskDot;
                        break;
                    case "phoneMaskHyphen":
                        mask = CoreFormsUiConfig.phoneMaskHyphen;
                        break;
                    default:
                        mask = CoreFormsUiConfig.phoneMask;
                    }
                }
                iElement.attr("maxlength", mask.length);
                var checkValidity = function (unmaskedValue, rawValue) {
                    var valid = false;
                    if (angular.isUndefined(rawValue) || rawValue === '') {
                        valid = true;
                    } else if (unmaskedValue) {
                        valid = (unmaskedValue.length === 10);
                    }
                    ctrl.$setValidity('invalidPhoneNumber', validPhoneNumber);
                    ctrl.$setValidity('mask', valid);
                    return valid;
                };
                var handleKeyup = function (evt) {

                    if (evt && evt.keyCode === keymap.KEY.SHIFT) {
                        return;
                    }

                    var index, formattedNumber;
                    if (ctrl.$modelValue) {
                        formattedNumber = ctrl.$modelValue;
                    } else {
                        formattedNumber = iElement.val();
                    }
                    if (!formattedNumber.length && currentKey === '') {
                        return;
                    }
                    var maskLength, inputNumbers, maskArray, tempArray, maskArrayLength;
                    tempArray = [];
                    maskArray = mask.split("");
                    maskArrayLength = maskArray.length;
                    maskLength = formattedNumber.substring(0, mask.length);
                    inputNumbers = formattedNumber.replace(/[^0-9]/g, "").split("");
                    for (index = 0; index < maskArrayLength; index++) {
                        tempArray.push(maskArray[index] === "_" ? inputNumbers.shift() : maskArray[index]);
                        if (inputNumbers.length === 0) {
                            break;
                        }
                    }
                    formattedNumber = tempArray.join("");
                    if (formattedNumber === '(') {
                        formattedNumber = '';
                    }

                    if ( (angular.isDefined(evt) && evt.which) && currentKey !== '') {
                        if (maskArray[0] === currentKey && formattedNumber === '') {
                            formattedNumber = '(';
                        } else if (maskArray[0] === currentKey && formattedNumber === '') {
                            formattedNumber = formattedNumber + currentKey;
                        } else if (maskArray[formattedNumber.length] === currentKey) {
                            formattedNumber = formattedNumber + currentKey;
                        }
                        currentKey = '';
                    }

                    ctrl.$setViewValue(formattedNumber);
                    ctrl.$render();
                    return formattedNumber;
                };


                // since we are only allowing 0-9, why even let the keypress go forward?
                // also added in delete... in case they want to delete :)
                var handlePress = function (e) {
                    if (e.which) {
                        if ((e.which < 48 || e.which > 57) && (e.which < 96 || e.which > 105)) {
                            if (e.which !== keymap.KEY.BACKSPACE && e.which !== keymap.KEY.TAB && e.which !== keymap.KEY.DELETE && e.which !== keymap.KEY.ENTER && e.which !== keymap.KEY.LEFT && e.which !== keymap.KEY.RIGHT &&
                                // Allow: Ctrl+V/v
                                (!(e.ctrlKey) && (e.which !== '118' || e.which !== '86')) &&
                                // Allow: Ctrl+C/c
                                (!(e.ctrlKey) && (e.which !== '99' || e.which !== '67')) &&
                                // Allow: Ctrl+X/x
                                (!(e.ctrlKey) && (e.which !== '120' || e.which !== '88')) &&
                                /* 229 key code will sent as placeholder key for andriod devices */
                                (e.which != 229 )) {
                                e.preventDefault ? e.preventDefault() : e.returnValue = false;
                                validPhoneNumber = false;
                            }
                        } else {
                            validPhoneNumber = true;
                        }

                        setCurrentKey(e);
                    }
                    scope.$apply();
                };
                // i moved this out because i thought i might need focus as well..
                // to handle setting the model as the view changes
                var parser = function (fromViewValue) {
                    var letters = /^[A-Za-z]+$/;
                    var numbers = /^[0-9]+$/;
                    if (angular.isUndefined(fromViewValue) || fromViewValue === '') {
                        validPhoneNumber = true;
                    } else {
                        if (fromViewValue.match(letters)) {
                            validPhoneNumber = false;
                        }
                        if (fromViewValue.match(numbers)) {
                            validPhoneNumber = true;
                        }
                    }
                    var clean = "";
                    if (fromViewValue && fromViewValue.length > 0) {
                        clean = fromViewValue.replace(/[^0-9]/g, '');
                    }
                    checkValidity(clean, fromViewValue);
                    return clean;
                };

                //to handle reading the model and formatting it
                var formatter = function (fromModelView) {
                    var input = '';
                    checkValidity(fromModelView);
                    if (fromModelView) {
                        input = handleKeyup();
                    }
                    return input;
                };

                var setCurrentKey = function (e) {
                    switch (e.which) {
                    case 189:
                    case 109:
                        currentKey = '-';
                        break;
                    case 190:
                    case 110:
                        currentKey = '.';
                        break;
                    case 57:
                        if (e.shiftKey === true) {
                            currentKey = '(';
                        }
                        break;
                    case 48:
                        if (e.shiftKey === true) {
                            currentKey = ')';
                        }
                        break;
                    case 32:
                        currentKey = ' ';
                        break;
                    }
                };

                if (angular.isDefined(scope.ngModel)) {
                    parser(scope.ngModel);
                }

                ctrl.$parsers.push(parser);
                ctrl.$formatters.push(formatter);
                iElement.bind('keyup', handleKeyup);
                iElement.bind('keydown', handlePress);
            }
        };
}]);
/** 
 * @ngdoc directive 
 * @name Template.att:Profile Blocks 
 * 
 * @description 
 *  <file src="src/profileBlockTemplate/docs/readme.md" /> 
 * @example 
 *  <section id="code">  
        <example module="b2b.att"> 
            <file src="src/profileBlockTemplate/docs/demo.html" /> 
            <file src="src/profileBlockTemplate/docs/demo.js" /> 
       </example>  
    </section>  
 *  
 */   

angular.module('b2b.att.profileBlockTemplate', [])
    
     
  
/**
 * @ngdoc directive
 * @name Layouts.att:profileCard
 *
 * @description
 *  <file src="src/profileCard/docs/readme.md" />
 *
 * @usage
 *  <b2b-profile-card></b2b-profile-card>
 *
 * @example
    <section id="code">   
        <example module="b2b.att">
            <file src="src/profileCard/docs/demo.html" />
            <file src="src/profileCard/docs/demo.js" />
        </example>
    </section>
 */

angular.module('b2b.att.profileCard', ['b2b.att'])
.constant('profileStatus',{
    status: {
        ACTIVE: {
            status: "Active",
            color: "green"
        },
        DEACTIVATED: {
            status: "Deactivated",
            color: "red"
        },
        LOCKED: {
            status: "Locked",
            color: "red"
        },
        IDLE: {
            status: "Idle",
            color: "yellow"
        },
        PENDING: {
            status: "Pending",
            color: "blue"
        }
    },
    role: "COMPANY ADMINISTRATOR"

})
.directive('b2bProfileCard',['$http','$q','profileStatus', function($http,$q,profileStatus) {
   return {
        restrict: 'EA',
        replace: 'true',
        templateUrl: function(element, attrs){
            if(!attrs.addUser){
                return 'b2bTemplate/profileCard/profileCard.html';
            }
            else{
                return 'b2bTemplate/profileCard/profileCard-addUser.html';
            }
        },
        scope: {
            profile:'=',
            characterLimit: '@'
        },
        link: function(scope, elem, attr){
        	scope.characterLimit = parseInt(attr.characterLimit, 10) || 25;
        	scope.shouldClip = function(str) {
        		return str.length > scope.characterLimit;
        	};

        	scope.showEmailTooltip = false;

            scope.image=true;
            function isImage(src) {
                var deferred = $q.defer();
                var image = new Image();
                image.onerror = function() {
                    deferred.reject(false);
                };
                image.onload = function() {
                    deferred.resolve(true);
                };
                if(src !== undefined && src.length>0 ){
                    image.src = src;
                } else {
                     deferred.reject(false);
                }
                return deferred.promise;
            }
            if(!attr.addUser){
            scope.image=false;
            isImage(scope.profile.img).then(function(img) {
                scope.image=img;
            });
            var splitName=(scope.profile.name).split(' ');
            scope.initials='';
            for(var i=0;i<splitName.length;i++){
                scope.initials += splitName[i][0];
            }
            if(scope.profile.role.toUpperCase() === profileStatus.role){
                scope.badge=true;
            }
            var profileState=profileStatus.status[scope.profile.state.toUpperCase()];
            if(profileState) {
                scope.profile.state=profileStatus.status[scope.profile.state.toUpperCase()].status;
                scope.colorIcon=profileStatus.status[scope.profile.state.toUpperCase()].color;
                if(scope.profile.state.toUpperCase()===profileStatus.status.PENDING.status.toUpperCase()||scope.profile.state.toUpperCase()===profileStatus.status.LOCKED.status.toUpperCase()){
                        scope.profile.lastLogin=scope.profile.state;
                }
            }
            var today=new Date().getTime();
            var lastlogin=new Date(scope.profile.lastLogin).getTime();
            var diff=(today-lastlogin)/(1000*60*60*24);
            if(diff<=1){
                scope.profile.lastLogin="Today";
            }
            else if(diff<=2){
                scope.profile.lastLogin="Yesterday";
            }
        }
    }
};
}]);
/**
 * @ngdoc directive
 * @name Forms.att:radios
 *
 * @description
 *  <file src="src/radios/docs/readme.md" />
 *
 * @usage
 *  See demo section
 *
 * @param {boolean} refreshRadioGroup - A trigger that recalculates and updates the accessibility roles on radios in a group.
 * 
 * @example
    <section id="code">
               <b>HTML + AngularJS</b>
               <example module="b2b.att">
                <file src="src/radios/docs/demo.html" />
                <file src="src/radios/docs/demo.js" />
               </example>
            </section>
 */
angular.module('b2b.att.radios', ['b2b.att.utilities'])
.directive('b2bRadioGroupAccessibility', ['$timeout', 'b2bUserAgent', function($timeout, b2bUserAgent) {
    return {
        restrict: "A",
        scope: {
            refreshRadioGroup: "=",
        },
        link: function(scope, ele, attr) {

            var roleRadioElement, radioProductSelectElement, radioInputTypeElement;

            $timeout(calculateNumberOfRadio);

            scope.$watch('refreshRadioGroup', function(value) {
                if (value === true) {
                    addingRoleAttribute();
                    $timeout(calculateNumberOfRadio);
                    scope.refreshRadioGroup = false;
                } else {
                    return;
                }
            })


            function calculateNumberOfRadio() {
                roleRadioElement = ele[0].querySelectorAll('[role="radio"]');

                radioProductSelectElement = ele[0].querySelectorAll('[role="radiogroup"] li.radio-box');

                radioInputTypeElement = ele[0].querySelectorAll('input[type="radio"]');

                for (var i = 0; i < radioInputTypeElement.length; i++) {
                    var isChecked = radioInputTypeElement[i].checked ? 'true' : 'false';
                    var isDisabled = radioInputTypeElement[i].disabled ? 'true' : 'false';
                    var numOfx = i + 1 + ' of ' + radioInputTypeElement.length;
                    angular.element(roleRadioElement[i]).attr({
                        'aria-checked': isChecked,
                        'aria-disabled': isDisabled,
                        'data-opNum': numOfx
                    });
                    if (b2bUserAgent.notMobile() || (radioProductSelectElement && radioProductSelectElement.length > 0) ) {
                        angular.element(roleRadioElement[i]).removeAttr("role");
                    }

                    if (radioProductSelectElement.length) {
                        isChecked === 'true' ? angular.element(radioProductSelectElement[i]).addClass('active') : angular.element(radioProductSelectElement[i]).removeClass('active');
                    }

                    if (/Android/i.test(navigator.userAgent)) {
                        angular.element(roleRadioElement[i]).append('<span class="hidden-spoken">. ' + numOfx + '.</span>');
                    }


                    angular.element(radioInputTypeElement[i]).bind('click', radioStateChangeonClick);

                }
            }

            function addingRoleAttribute() {
                if(radioInputTypeElement){
                    for (var i = 0; i < radioInputTypeElement.length; i++) {
                        if (b2bUserAgent.notMobile() || (radioProductSelectElement && radioProductSelectElement.length > 0) ) {
                            angular.element(roleRadioElement[i]).attr("role","radio");
                        }
                    }
                }  
            }

            function radioStateChangeonClick() {
                for (var i = 0; i < radioInputTypeElement.length; i++) {
                    var isChecked = radioInputTypeElement[i].checked ? 'true' : 'false';
                    var isDisabled = radioInputTypeElement[i].disabled ? 'true' : 'false';
                    if (radioProductSelectElement.length) {
                        isChecked === 'true' ? angular.element(radioProductSelectElement[i]).addClass('active') : angular.element(radioProductSelectElement[i]).removeClass('active');
                    }
                    angular.element(roleRadioElement[i]).attr({
                        'aria-checked': isChecked,
                        'aria-disabled': isDisabled
                    });
                }

            }
        }
    }

}]);

/**
 * @ngdoc directive
 * @name Misc.att:reorderList
 *
 * @description
 *  <file src="src/reorderList/docs/readme.md" />
 *
 * @param {int} currentIndex - Current index of focussed list item. 
 * @param {Array} listData - Data of list items. Should include full data regardless if HTML will be filtered.

 * @example
 *  <section id="code">   
     <example module="b2b.att">
     <file src="src/reorderList/docs/demo.html" />
     <file src="src/reorderList/docs/demo.js" />
     </example>
    </section>
 *
 */
angular.module('b2b.att.reorderList', ['b2b.att.utilities'])
.directive('b2bReorderList', ['keymap', 'b2bDOMHelper', '$rootScope','$timeout','$document', function(keymap, b2bDOMHelper, $rootScope, $timeout, $document) {
            return {
                restrict: 'AE',
                transclude: true,
                replace: true,
                scope: {
                    currentIndex: '=', 
                    listData: '='                
                },
                templateUrl: 'b2bTemplate/reorderList/reorderList.html',
                link: function(scope, elem, attr) {
                    //in case of a filter, scope.listboxData array holds only the filtered items 
                    // removal/readdition of items while filtering is handled in b2bReorderListItem directive                    
                    scope.listboxData = [];                        
                    scope.backup = angular.copy(scope.listData);                       
                    if (attr.ariaMultiselectable !== undefined || attr.ariaMultiselectable === 'true') {
                        scope.multiselectable = true;
                    } else {
                        scope.multiselectable = false;
                    }

                    var shiftKey = false;
                    var elements = [];
                    var prevDirection = undefined; // previous direction is used for an edge case when shifting
                    var shiftKeyPressed = false; // Used to handle shift clicking
                    var ctrlKeyPressed = false;
                    scope.firstAvailableIndex = 0;                    
                    
                    function isSelected(item) {
                        if (item.selected === true ) {
                            return true;
                        }
                    }  
                    var firstSelected,lastSelected,firstAvailable,lastAvailable;
                    scope.moveUp = false;
                    scope.moveDown = false;
                   scope.init = function(){
                        scope.$parent.selectedCount = 0;
                        firstSelected=-1;
                        lastSelected=-1;
                        firstAvailable=-1;
                        lastAvailable =-1;
                        scope.$parent.selectedCount =0;
                        scope.lastAvailableIndex= scope.listboxData.length-1;
                        for(var i=0;i<scope.listboxData.length;i++){
                            if(scope.listboxData[i].selected){
                                if(firstSelected==-1){
                                    firstSelected=i;
                                }
                                lastSelected = i;
                                scope.$parent.selectedCount++;                                
                            }else{                               
                                if(firstAvailable ==-1){
                                    firstAvailable=i;
                                }
                                lastAvailable = i;                                
                            }
                        }    
                         if(lastSelected>firstAvailable && firstAvailable != -1){
                            scope.moveUp = true;
                        }else{
                            scope.moveUp = false;
                        }
                        if(firstSelected<lastAvailable && firstSelected !=-1){
                            scope.moveDown = true;
                        }else{
                            scope.moveDown = false;
                        }   

                   }; 

                   function get(position,val){                      
                    if(position=="first"){
                        for(var i = 0 ;i<scope.listboxData.length;i++){
                            if(scope.listboxData[i].selected == val){
                                return i;
                            }
                        }
                        return -1;
                    }else if(position =="last"){
                        for(var i = scope.listboxData.length-1 ;i>=0;i--){
                            if(scope.listboxData[i].selected == val){
                                return i;
                            }
                        }
                        return -1;
                    }   
                   }  

                   function enableButton(index,selected){
                        if(selected){
                            if(index<firstSelected || firstSelected ==-1){
                                firstSelected =index;                                   
                            }
                            if(index == firstAvailable){
                                firstAvailable = get('first',false);
                            }
                            if(index>lastSelected || lastSelected ==-1){
                                lastSelected =index;                                
                            }
                            if(index == lastAvailable){
                                lastAvailable = get('last',false);
                            }                               
                        }else{
                            if(index<firstAvailable || firstAvailable ==-1){
                                firstAvailable =index;                                  
                            }
                            if(index == firstSelected){
                                firstSelected = get('first',true);
                            }
                            if(index>lastAvailable || lastAvailable ==-1){
                                lastAvailable =index;                                   
                            }
                            if(index == lastSelected){
                                lastSelected = get('last',true);
                            }
                        }
                        if(lastSelected>firstAvailable && firstAvailable != -1){
                            scope.moveUp = true;
                        }else{
                            scope.moveUp = false;
                        }
                        if(firstSelected<lastAvailable && firstSelected !=-1){
                            scope.moveDown = true;
                        }else{
                            scope.moveDown = false;
                        }
                        if(scope.$parent.selectedCount === 0){
                            scope.moveUp = false;
                            scope.moveDown = false;
                        }
                   };                        

                    function selectItems(startIndex, endIndex, forceValue) {
                        for (var i = startIndex; i < endIndex; i++) {                              
                            if(!scope.listboxData[i].selected && forceValue){
                                scope.$parent.selectedCount++;
                                
                            }else if(scope.listboxData[i].selected && !forceValue){
                                scope.$parent.selectedCount--;
                                
                            }                                                           
                            scope.listboxData[i].selected = forceValue;    
                            enableButton(i,scope.listboxData[i].selected);
                        }
                        if (!scope.$$phase) {
                            scope.$apply();
                        }
                    }                      

                    elem.children().eq(0).bind('focus', function(evt) { 
                        scope.currentIndex = scope.firstAvailableIndex;
                        if (!scope.$$phase) {
                            scope.$apply();
                        }
                    });
                    scope.$watch('currentIndex',function(){
                        if(scope.currentIndex >-1){
                            elem.children().eq(0).children()[parseInt(attr.fixedOptions)+scope.currentIndex].focus();                           
                        }
                    });
                    /*elem.children().eq(0).bind('mouseover', function(evt) {                         
                        var index = parseInt(evt.target.parentElement.getAttribute("index"), 10);
                        if (index === undefined || isNaN(index)) {                         
                                return;                                                         
                        }  
                        scope.currentIndex = index;

                        if (!scope.$$phase) {
                            scope.$apply();
                        }
                    });*/
                   /* elem.children().eq(0).bind('mouseleave', function(evt) { 
                        scope.currentIndex = -1;
                        if (!scope.$$phase) {
                            scope.$apply();
                        }
                    });*/
                    elem.children().eq(0).bind('keyup', function(evt) {
                        if (evt.keyCode === keymap.KEY.SHIFT) {
                            shiftKeyPressed = false;
                        } else if (evt.keyCode === keymap.KEY.CTRL) {
                            ctrlKeyPressed = false;
                        }
                    }); 
                    
                    var lastSelected = 0;
                    elem.children().eq(0).bind('click', function(evt) {
                        var index = parseInt(evt.target.parentElement.getAttribute("index"), 10);
                        if (index === undefined || isNaN(index)) {                         
                                return;                                                         
                        }                       
                        if (shiftKeyPressed) {                           
                            selectItems(Math.min(index, lastSelected), Math.max(index, lastSelected)+1, true);
                        } else if (ctrlKeyPressed) {
                            if(scope.listboxData[index].selected){
                                scope.$parent.selectedCount--;
                            }else{
                                scope.$parent.selectedCount++;
                            }
                            scope.listboxData[index].selected = !scope.listboxData[index].selected;
                            firstSelected = -1; 
                            lastSelected = -1;
                            for(var i = 0 ;i<scope.listboxData.length;i++){
                                if(scope.listboxData[i].selected == true){
                                    lastSelected = i;
                                    if(firstSelected === -1){
                                        firstSelected = i;
                                    }
                                }
                            }
                            enableButton(index,scope.listboxData[index].selected);                                   
                        } else {    
                            selectItems(0, scope.listboxData.length, false);
                            scope.listboxData[index].selected = true;
                            scope.$parent.selectedCount++;
                            enableButton(index,true);
                            lastSelected = index;
                        }                        
                        scope.currentIndex = index;
                        $timeout(function () {
                            if (!scope.$$phase) {
                                scope.$apply();
                            }
                        },10);
                        
                       evt.stopPropagation();
                    });  
                    
                    function getNext  (regex,prop, startIndex) {
                        startIndex = startIndex && startIndex > -1 ? startIndex : 0;
                        for (var index = startIndex; index < scope.listboxData.length; index++) {
                            if (scope.listboxData[index][prop].toString().match(regex) ){
                                return index;
                            }                            
                        }                
                        for (var index = 0; index < startIndex; index++) {
                            if (scope.listboxData[index][prop].toString().match(regex)) {
                                return index;
                            }
                        }                
                        return -1;
                    }

                    var timerPromise,searchString="" ;

                    elem.children().eq(0).bind('keydown', function(evt) {
                        var keyCode = evt.keyCode;                                              
                        if (keyCode === keymap.KEY.SHIFT) {
                            shiftKeyPressed = true;
                        } else if (evt.keyCode === keymap.KEY.CTRL) {
                            ctrlKeyPressed = true;
                        }
                                                    
                        if(((keyCode >=65 && keyCode<=90) || (keyCode >=97 && keyCode<=122) ||(keyCode >=48 && keyCode<=57)) && !evt.ctrlKey){
                            searchString += keymap.MAP[keyCode];
                            var regex = new RegExp("^" + searchString, "i");                                
                            var next = getNext(regex,'title',parseInt(scope.currentIndex)+1);                                                                     
                            if(next != -1){
                                scope.currentIndex = next;
                            }
                            if(timerPromise){
                                $timeout.cancel(timerPromise);
                            }                               
                            timerPromise = $timeout (function(){
                                searchString ="";
                            },600,false);
                            if (!scope.$$phase) {
                                scope.$apply();
                            }
                        }
                        switch(keyCode) {
                            case 65: // A key
                            {
                                if (scope.multiselectable && evt.ctrlKey) {
                                    var arr = scope.listboxData.filter(isSelected);
                                    var val = !(arr.length === scope.listboxData.length);
                                    selectItems(0,scope.listboxData.length,val);

                                    evt.preventDefault();
                                    evt.stopPropagation();

                                    if (!scope.$$phase) {
                                        scope.$apply();
                                    }  
                                }
                                break;
                            }
                            case keymap.KEY.END:
                            {
                                if (scope.multiselectable && evt.ctrlKey && evt.shiftKey) {                                    
                                    selectItems(scope.currentIndex,scope.listboxData.length,true);                                   
                                }else{
                                    scope.currentIndex = scope.lastAvailableIndex;
                                }
                                evt.preventDefault();
                                evt.stopPropagation();
                                if (!scope.$$phase) {
                                    scope.$apply();
                                }
                                break;
                            }
                            case keymap.KEY.HOME: 
                            {
                                if (scope.multiselectable && evt.ctrlKey && evt.shiftKey) {
                                    selectItems(0,scope.currentIndex+1,true);
                                }else{
                                    scope.currentIndex = scope.firstAvailableIndex;
                                }
                                evt.preventDefault();
                                evt.stopPropagation();

                                if (!scope.$$phase) {
                                    scope.$apply();
                                }
                                break;
                            }
                            case keymap.KEY.LEFT:
                            case keymap.KEY.UP:
                            {
                                if (scope.currentIndex == scope.firstAvailableIndex) {
                                    evt.preventDefault();
                                    evt.stopPropagation();
                                    return;
                                }
                               scope.currentIndex--;

                                if (scope.multiselectable && (evt.shiftKey )) {                                    
                                   
                                    if (prevDirection === 'DOWN') { 
                                        if(scope.listboxData[scope.currentIndex+1].selected){
                                            scope.$parent.selectedCount--;
                                        }else{
                                            scope.$parent.selectedCount++;
                                        }
                                        scope.listboxData[scope.currentIndex+1].selected = !scope.listboxData[scope.currentIndex+1].selected;
                                    enableButton(scope.currentIndex+1, scope.listboxData[scope.currentIndex+1].selected);
                                    } 
                                    if(scope.listboxData[scope.currentIndex].selected){
                                            scope.$parent.selectedCount--;
                                        }else{
                                            scope.$parent.selectedCount++;
                                    }                                   
                                    scope.listboxData[scope.currentIndex].selected = !scope.listboxData[scope.currentIndex].selected;
                                    enableButton(scope.currentIndex, scope.listboxData[scope.currentIndex].selected);
                                    prevDirection = 'UP';
                                }
                                
                                if(!scope.$$phase) {
                                    scope.$apply();
                                }
                                evt.preventDefault();
                                evt.stopPropagation();
                                break;
                            }
                            case keymap.KEY.RIGHT:
                            case keymap.KEY.DOWN:
                            {
                                if (scope.currentIndex == scope.lastAvailableIndex) {
                                    evt.preventDefault();
                                    evt.stopPropagation();
                                    return;
                                }                              
                                scope.currentIndex++;                                
                                if (scope.multiselectable && evt.shiftKey) {                                 
                                    enableButton(scope.currentIndex,scope.listboxData[scope.currentIndex].selected);
                                    if (prevDirection === 'UP') {     
                                        if(scope.listboxData[scope.currentIndex-1].selected){
                                            scope.$parent.selectedCount--;
                                        }else{
                                            scope.$parent.selectedCount++;
                                        }                                  
                                        scope.listboxData[scope.currentIndex-1].selected = !scope.listboxData[scope.currentIndex-1].selected;                                  
                                        enableButton(scope.currentIndex-1,scope.listboxData[scope.currentIndex-1].selected);
                                    }
                                    if(scope.listboxData[scope.currentIndex].selected){
                                        scope.$parent.selectedCount--;
                                    }else{
                                        scope.$parent.selectedCount++;
                                    }                                   
                                    scope.listboxData[scope.currentIndex].selected = !scope.listboxData[scope.currentIndex].selected;    
                                    enableButton(scope.currentIndex,scope.listboxData[scope.currentIndex].selected);
                                    prevDirection = 'DOWN';
                                } 
                               
                                if(!scope.$$phase) {
                                    scope.$apply();
                                }
                                evt.preventDefault();
                                evt.stopPropagation();
                                break;
                            }
                            case keymap.KEY.SPACE: 
                                if (scope.multiselectable){
                                    if(scope.listboxData[scope.currentIndex].selected){
                                        scope.$parent.selectedCount--;

                                    }else{
                                        scope.$parent.selectedCount++;
                                    } 
                                    scope.listboxData[scope.currentIndex].selected = !scope.listboxData[scope.currentIndex].selected;
                                    enableButton(scope.currentIndex,scope.listboxData[scope.currentIndex].selected);
                                }   
                                if(!scope.$$phase) {
                                    scope.$apply();
                                }
                                evt.preventDefault();
                                evt.stopPropagation();
                                break;
                            case keymap.KEY.TAB:
                                if(evt.shiftKey) {
                                    var previousElement = b2bDOMHelper.previousElement(elem.parent().parent(), true);
                                    evt.preventDefault();
                                    previousElement.focus();
                                }
                                scope.currentIndex =-1;
                                if(!scope.$$phase) {
                                    scope.$apply();
                                }                                    
                                break;                                   
                            default:
                                break;
                        }
                    });               
                    
                },
                controller : ['$scope',function($scope){
                    function swap (from,to){
                        var temp = $scope.listboxData[from],j = $scope.listboxData[from].parentIndex,k=$scope.listboxData[to].parentIndex;
                        $scope.listboxData[from] = $scope.listboxData[to];
                        $scope.listboxData[to] = temp;
                        temp = $scope.listData[j];
                        $scope.listData[j] = $scope.listData[k];
                        $scope.listData[k] = temp;
                        $scope.listboxData[to].parentIndex = k;
                        $scope.listboxData[from].parentIndex = j;                        
                    }
                    function handleFilter(temp){
                        var filteredIndices = [];
                        var i =0;
                        for(;i<$scope.listData.length;i++){
                            if(!$scope.listData[i].notFiltered){
                                filteredIndices.push(i);
                            }
                        }
                        for(i=0;i<filteredIndices.length;i++){
                            temp.splice(filteredIndices[i],0,$scope.listData[filteredIndices[i]]);
                        }
                        for(i=0;i<$scope.listboxData.length;i++){
                            $scope.listboxData[i].parentIndex = temp.indexOf($scope.listboxData[i]);
                        }
                    }

                    $scope.shiftTo = function(to){
                        if(to == 'up'){                           
                            for(var i=1;i<$scope.listboxData.length;i++){
                                if($scope.listboxData[i].selected){ 
                                    if($scope.listboxData[i-1] == undefined || $scope.listboxData[i-1].selected==true){
                                        continue;
                                    }
                                    swap(i-1,i);                                                                                                
                                }                           
                            }                         
                            $scope.init();
                            if($scope.moveUp == false && $scope.moveDown == false){
                                document.getElementById('resetLink').focus();
                            }else if($scope.moveUp == false && $scope.moveDown){
                                document.getElementById('moveDown').focus();
                            }
                        }else if(to == 'down'){
                            for(var i=$scope.listData.length-2;i>=0;i--){
                                if($scope.listData[i].selected){
                                    if($scope.listData[i+1] == undefined || $scope.listData[i+1].selected==true){
                                            continue;
                                    }
                                    swap(i,i+1);
                                }                           
                            }                            
                            $scope.init();
                             if($scope.moveDown == false){
                                document.getElementById('resetLink').focus();
                            }
                        }else if (to == 'top'){
                            var temp=[];
                            for(var i=$scope.listboxData.length-1,j=0;i>=0;i--,j++){
                                if($scope.listboxData[i].selected){
                                    temp.unshift($scope.listboxData[i]);                    
                                }
                                if(!$scope.listboxData[j].selected ){
                                    temp.push($scope.listboxData[j]);       
                                }                                                  
                            }                           
                            $scope.listboxData=temp;
                            temp=[];
                            for(var i=0;i<$scope.listboxData.length;i++){
                                temp.push($scope.listData[$scope.listboxData[i].parentIndex]);                                                 
                            }
                            handleFilter(temp);
                            $scope.listData = temp;
                            $scope.init();
                            if($scope.moveUp == false && $scope.moveDown == false){
                                document.getElementById('resetLink').focus();
                            }else if($scope.moveUp == false && $scope.moveDown){
                                document.getElementById('moveDown').focus();
                            }
                        }else if (to == 'bottom'){                  
                            var temp=[];
                            for(var i=$scope.listboxData.length-1,j=0;i>=0;i--,j++){
                                if(!$scope.listboxData[i].selected ){
                                    temp.unshift($scope.listboxData[i]);                    
                                }
                                if($scope.listboxData[j].selected){
                                    temp.push($scope.listboxData[j]);   
                                }                        
                            }
                            $scope.listboxData=temp;
                            temp=[];
                            for(var i=0;i<$scope.listboxData.length;i++){
                                temp.push($scope.listData[$scope.listboxData[i].parentIndex]);                                                 
                            }
                            handleFilter(temp);
                            $scope.listData = temp;                            
                            $scope.init();
                             if($scope.moveDown == false){
                                document.getElementById('resetLink').focus();
                            }
                        }
                    };
                    $scope.reset = function(){
                        $scope.listboxData = [];
                        $scope.listData = angular.copy($scope.backup);                        
                        $scope.init();
                    };
                     
                }]
            };
            }])
    .directive('b2bReorderListItem',function(){
        return{
            restrict: 'A',
            scope:false,
            link : function(scope,elem,attr){  
            // This directive stabilizes data if a filter is applied by the developers
                function resetParentIndex(arr,parent){
                    for(var i=0;i<arr.length;i++){
                        arr[i].parentIndex = parent.indexOf(arr[i]);
                    }
                }           
                scope.$parent.$parent.listData[attr.b2bReorderListItem].notFiltered = true;
                scope.$parent.$parent.listboxData.splice(attr.index,0,scope.$parent.$parent.listData[attr.b2bReorderListItem]);
                scope.$parent.$parent.listboxData[attr.index].parentIndex = parseInt(attr.b2bReorderListItem);
                scope.$parent.$parent.init();
                var data = scope.$parent.$parent.listData[attr.b2bReorderListItem];
                scope.$on('$destroy',function(){
                    var index = scope.$parent.$parent.listboxData.indexOf(data);                                    
                    scope.$parent.$parent.listboxData.splice(index,1);
                    index =scope.$parent.$parent.listData.indexOf(data);
                    if(index != -1){
                        scope.$parent.$parent.listData[attr.b2bReorderListItem].notFiltered = false;
                    }
                    scope.$parent.$parent.init();
                    resetParentIndex(scope.$parent.$parent.listboxData,scope.$parent.$parent.listData);
                    scope.$parent.$parent.currentIndex =scope.$parent.$parent.firstAvailableIndex; 
                });
            }
        }
    });
/**
 * @ngdoc directive
 * @name Forms.att:searchField
 *
 * @description
 *  <file src="src/searchField/docs/readme.md" />
 *
 * @usage
 *  <div b2b-search-field dropdown-list="listdata" on-click-callback="clickFn(value)" class="span12" input-model='typedString' config-obj='keyConfigObj'></div>
 *
 * @example
 <section id="code">
    <example module="b2b.att">
    <file src="src/searchField/docs/demo.html" />
    <file src="src/searchField/docs/demo.js" />
    </example>
</section>
 */

angular.module('b2b.att.searchField', ['b2b.att.utilities', 'b2b.att.position'])
    .filter('b2bFilterInput', [function() {
        return function(list, str, keyArray, displayListKey, isContainsSearch, searchSeperator) {
            var res = [];
            var searchLabel;
            var searchCondition;
            var conditionCheck = function(searchSeperator, listItem, displayListKey, splitString) {
                var displayTitle = null;
                if (splitString) {
                    for (var i = 0; i < displayListKey.length; i++) {
                        if (i <= 0) {
                            displayTitle = listItem[displayListKey[i]].toLowerCase().indexOf(splitString[i].toLowerCase()) > -1;
                        } else {
                            displayTitle = (splitString[i]) ? displayTitle && listItem[displayListKey[i]].toLowerCase().indexOf(splitString[i].toLowerCase().trim()) > -1 : displayTitle;
                        }
                    }
                } else {
                    angular.forEach(displayListKey, function(value) {
                        if (!displayTitle) {
                            displayTitle = listItem[value];
                        } else {
                            displayTitle = displayTitle + (listItem[value] ? searchSeperator + ' ' + listItem[value] : '');
                        }
                    });
                }
                return displayTitle;
            }
            angular.forEach(list, function(listItem) {
                var splitString = str.indexOf(searchSeperator) > -1 ? str.split(searchSeperator) : false;
                var displayList = conditionCheck(searchSeperator, listItem, displayListKey, splitString)
                for (var i = 0; i < keyArray.length; i++) {
                    searchLabel = keyArray[i];
                    if (listItem[searchLabel]) {
                        if (isContainsSearch) {
                            var displaySearchList = listItem[searchLabel].toLowerCase().indexOf(str.toLowerCase()) > -1;
                            if (splitString.length > 1) {
                                displaySearchList = (splitString.length <= keyArray.length) ? displayList : false;
                            }
                            searchCondition = displaySearchList;
                        } else {
                            searchCondition = listItem[searchLabel].match(new RegExp('^' + str, 'gi'));
                        }
                        if (searchCondition) {
                            res.push({
                                'title': conditionCheck(searchSeperator, listItem, displayListKey),
                                'valueObj': listItem
                            });
                            break;
                        }
                    }
                }
            });
            return res;
        };
    }]).directive('b2bSearchField', ['$filter', 'b2bFilterInputFilter', 'keymap', '$documentBind', '$isElement', '$document', 'events', '$timeout', function($filter, b2bFilterInput, keymap, $documentBind, $isElement, $document, events, $timeout) {
        return {
            restrict: 'A',
            scope: {
                dataList: '=dropdownList',
                onClickCallback: '&',
                inputModel: '=',
                configObj: '=',
                objModel: '=',
                inputDeny: '=?',
                disabled: '=?'
            },
            replace: true,
            templateUrl: 'b2bTemplate/searchField/searchField.html',
            controller: ['$scope', function($scope) {
                this.searchKeyArray = [];
                if ($scope.configObj.searchKeys) {
                    this.searchKeyArray = $scope.configObj.searchKeys;
                }
                if (angular.isUndefined($scope.disabled)) {
                    $scope.disabled = false;
                }
                this.triggerInput = function(searchString) {
                    $scope.originalInputModel = searchString;
                    if (searchString === '') {
                        $scope.currentIndex = -1;
                        $scope.filterList = [];
                        $scope.showListFlag = false;
                    } else if (searchString !== '' && !$scope.isFilterEnabled) {
                        $scope.filterList = $filter('b2bFilterInput')($scope.dataList, searchString, this.searchKeyArray, $scope.configObj.displayListDataKey, $scope.configObj.isContainsSearch, $scope.configObj.searchSeperator);
                        $scope.showListFlag = true;
                    }
                };
                this.denyRegex = function() {
                    return $scope.inputDeny;
                };
            }],
            link: function(scope, elem) {
                scope.isFilterEnabled = false;
                scope.showListFlag = false;
                scope.currentIndex = -1;
                scope.setCurrentIdx = function(idx) {
                    scope.currentIndex = idx;
                    if (idx > -1) {
                        scope.inputModel = scope.filterList[idx].title;
                        scope.objModel = scope.filterList[idx];
                    }
                };
                scope.isActive = function(index, dropdownLength) {
                    scope.dropdownLength = dropdownLength;
                    return scope.currentIndex === index;
                };
                scope.selectItem = function(idx) {
                    scope.setCurrentIdx(idx);
                    scope.onClickCallback({
                        value: scope.inputModel,
                        objValue: scope.objModel
                    });
                    scope.showListFlag = false;
                    $timeout(function() {
                        elem.find('div').find('input')[0].focus();
                    }, 150);
                };
                scope.startSearch = function() {
                    scope.onClickCallback({
                        value: scope.inputModel,
                        objValue: scope.objModel
                    });
                };
                var maxItemsLength = 9;
                scope.selectPrev = function() {
                     if (scope.currentIndex > 0 && scope.filterList.length > 0) {
                         // Scroll fix: Ensure we move down the ul's scrollTop by one element's pixels worth
                         // Checking if its less than 9 because in our suggestion list we only have height set to show max 10 elements
                         if (scope.currentIndex - 1 <= maxItemsLength) {
                             var ulElem = elem.find('ul');
                             ulElem[0].scrollTop -= 40; // 40 px
                             ulElem[0].scrollTop = Math.max(ulElem[0].scrollTop, 0);
                         }
                         scope.currentIndex = scope.currentIndex - 1;
                         scope.setCurrentIdx(scope.currentIndex);
                     } else if (scope.currentIndex === 0) {
                         scope.currentIndex = scope.currentIndex - 1;
                         scope.inputModel = scope.originalInputModel;
                         scope.isFilterEnabled = true;
                     }
                 };
                 scope.selectNext = function() {
                     if (scope.currentIndex < scope.configObj.noOfItemsDisplay - 1) {
                         if (scope.currentIndex < scope.filterList.length - 1) {
                             // Scroll fix: Ensure we move the ul's scrollTop by one element's pixels worth
                             // Checking if its greater than 9 because in our suggestion list we only have height set to show max 10 elements
                             if (scope.currentIndex + 1 >= maxItemsLength) {
                                 var ulElem = elem.find('ul');
                                 ulElem[0].scrollTop += 40; // 40 px
                             }
                             scope.currentIndex = scope.currentIndex + 1;
                             scope.setCurrentIdx(scope.currentIndex);
                         }
                     }
                 };
                scope.selectCurrent = function() {
                    scope.selectItem(scope.currentIndex);
                };
                scope.selectionIndex = function(e) {
                    switch (e.keyCode) {
                        case keymap.KEY.DOWN:
                            events.preventDefault(e);
                            scope.isFilterEnabled = true;
                            scope.selectNext();
                            break;
                        case keymap.KEY.UP:
                            events.preventDefault(e);
                            scope.isFilterEnabled = true;
                            scope.selectPrev();
                            break;
                        case keymap.KEY.ENTER:
                            events.preventDefault(e);
                            scope.isFilterEnabled = true;
                            scope.selectCurrent();
                            break;
                        case keymap.KEY.ESC:
                            events.preventDefault(e);
                            scope.isFilterEnabled = false;
                            scope.showListFlag = false;
                            scope.inputModel = '';
                            break;
                        default:
                            scope.isFilterEnabled = false;
                            break;
                    }
                    if (elem[0].querySelector('.filtercontainer')) {
                        elem[0].querySelector('.filtercontainer').scrollTop = (scope.currentIndex - 1) * 35;
                    }
                };
                scope.$watch('filterList', function(newVal, oldVal) {
                    if (newVal !== oldVal) {
                        scope.currentIndex = -1;
                    }
                });
                scope.blurInput = function() {
                    $timeout(function() {
                        scope.showListFlag = false;
                    }, 150);
                };
                var outsideClick = function(e) {
                    var isElement = $isElement(angular.element(e.target), elem.find('ul').eq(0), $document);
                    if (!isElement && document.activeElement.tagName.toLowerCase() !== 'input') {
                        scope.showListFlag = false;
                        scope.$apply();
                    }
                };
                $documentBind.click('showListFlag', outsideClick, scope);
            }
        };
    }])
    .directive('b2bSearchInput', [function() {
        return {
            restrict: 'A',
            require: ['ngModel', '^b2bSearchField'],
            link: function(scope, elem, attr, ctrl) {
                var ngModelCtrl = ctrl[0];
                var attSearchBarCtrl = ctrl[1];
                var REGEX = ctrl[1].denyRegex();
                var parser = function(viewValue) {
                    attSearchBarCtrl.triggerInput(viewValue);
                    return viewValue;
                };
                ngModelCtrl.$parsers.push(parser);

                if (REGEX !== undefined || REGEX !== '') {
                    elem.bind('input', function() {
                        var inputString = ngModelCtrl.$viewValue && ngModelCtrl.$viewValue.replace(REGEX, '');
                        if (inputString !== ngModelCtrl.$viewValue) {
                            ngModelCtrl.$setViewValue(inputString);
                            ngModelCtrl.$render();
                            scope.$apply();
                        }
                    });
                }
            }
        };
    }])
    .directive('b2bSearchFieldInput', ['$filter', 'b2bFilterInputFilter', 'keymap', '$documentBind', '$isElement', '$document', 'events', '$timeout', function($filter, b2bFilterInput, keymap, $documentBind, $isElement, $document, events, $timeout) {
        return {
            restrict: 'A',
            require: ['ngModel'],
            scope: {
                dataList: '=dropdownList',
                configObj: '=',
            },
            controller: ['$scope', function($scope) {
                
            }],
            link: function(scope, elem, attr, ctrl) {
                this.searchKeyArray = [];
                this.showAllOptions = false;
                if (scope.configObj.searchKeys) {
                    this.searchKeyArray = scope.configObj.searchKeys;
                }
                if (!angular.isUndefined(scope.configObj.showAllOptions)) {
                    this.showAllOptions = scope.configObj.showAllOptions;
                }
                this.triggerInput = function(searchString) {
                    if (angular.isUndefined(searchString)) {
                        searchString = '';
                    }
                    scope.configObj.originalInputModel = searchString;
                    if (searchString === '' && !scope.configObj.showAllOptions) {
                        scope.configObj.filterList = [];
                        scope.configObj.showListFlag = false;
                    } else if (searchString !== '' && !scope.isFilterEnabled || (scope.configObj.showAllOptions)) {
                        scope.configObj.filterList = $filter('b2bFilterInput')(scope.dataList, searchString, this.searchKeyArray, scope.configObj.displayListDataKey, scope.configObj.isContainsSearch, scope.configObj.searchSeperator);
                        scope.configObj.showListFlag = true;
                    }
                };
                var ngModelCtrl = ctrl[0];
                if(this.showAllOptions){
                    elem.bind('focus', function() {
                        viewValue = ngModelCtrl.$viewValue;
                        if (angular.isUndefined(viewValue)) {
                            viewValue = '';
                        }
                        triggerInput(viewValue);
                        scope.$apply();
                    });
                }
                var parser = function(viewValue) {
                    triggerInput(viewValue);
                    return viewValue;
                };
                ngModelCtrl.$parsers.push(parser);
            }
        };
    }]);

/**
 * @ngdoc directive
 * @name Buttons, links & UI controls.att:Seek bar
 *
 * @description
 *  <file src="src/seekBar/docs/readme.md" />
 *
 * @usage
 *  Horizontal Seek Bar
 *	<b2b-seek-bar min="0" max="400" step="1" skip-interval="1" data-ng-model="horizontalSeekBarVal" style="width:180px; margin: auto;" on-drag-end="onDragEnd()" on-drag-init="onDragStart()"></b2b-seek-bar>

 *	Vertical Seek Bar
 *	<b2b-seek-bar min="0" max="1" step="0.01" skip-interval="0.1" vertical data-ng-model="verticalSeekBarVal" style=" width: 6px; height: 180px; margin: auto;"></b2b-seek-bar>
 *
 * @example
    <section id="code">   
        <b>HTML + AngularJS</b>
        <example module="b2b.att">
            <file src="src/seekBar/docs/demo.html" />
            <file src="src/seekBar/docs/demo.js" />
        </example>
    </section>
 */

angular.module('b2b.att.seekBar', ['b2b.att.utilities','b2b.att.position'])
        .constant('b2bSeekBarConfig', {
            'min': 0,
            'max': 100,
            'step': 1,
            'skipInterval': 1
        })
        .directive('b2bSeekBar', ['$documentBind', 'events', 'b2bSeekBarConfig', 'keymap', '$compile', function($documentBind, events, b2bSeekBarConfig, keymap, $compile) {
                return {
                    restrict: 'AE',
                    replace: true,
                    require: 'ngModel',
                    templateUrl: 'b2bTemplate/seekBar/seekBar.html',
                    scope: {
                        onDragEnd: '&?',
                        onDragInit: '&?'
                    },
                    link: function(scope, elm, attr, ngModelCtrl) {
                        scope.isDragging = false;
                        scope.verticalSeekBar = false;
                        var min;
                        var max;
                        var step = b2bSeekBarConfig.step;
                        var skipInterval = b2bSeekBarConfig.skipInterval;
                        var knob = angular.element(elm[0].querySelector('.b2b-seek-bar-knob-container'));
                        var seekBarKnob = angular.element(knob[0].querySelector('.b2b-seek-bar-knob'));
                        var trackContainer = angular.element(elm[0].querySelector('.b2b-seek-bar-track-container'));
                        var trackFill = angular.element(elm[0].querySelector('.b2b-seek-bar-track-fill'));
                        var trackContainerRect = {};
                        var axisPosition;
                        var trackFillOrderPositioning;

                        if (angular.isDefined(attr.vertical)) {
                            scope.verticalSeekBar = true;
                            axisPosition = "clientY";
                        }
                        else {
                            scope.verticalSeekBar = false;
                            axisPosition = "clientX";
                        }
                        var getValidStep = function(val) {
                            val = parseFloat(val);
                            // in case $modelValue came in string number
                            if (angular.isNumber(val)) {
                                val = Math.round((val - min) / step) * step + min;
                                return Math.round(val * 1000) / 1000;
                            }
                        };

                        var getPositionToPercent = function(x) {
                            if (scope.verticalSeekBar) {
                                return Math.max(0, Math.min(1, (trackContainerRect.bottom - x) / (trackFillOrderPositioning)));
                            }
                            else {
                                return Math.max(0, Math.min(1, (x - trackContainerRect.left) / (trackFillOrderPositioning)));
                            }
                        };

                        var getPercentToValue = function(percent) {
                            return (min + percent * (max - min));
                        };

                        var getValueToPercent = function(val) {
                            return (val - min) / (max - min);
                        };

                        var getValidMinMax = function(val) {
                            return Math.max(min, Math.min(max, val));
                        };

                        var updateTrackContainerRect = function() {
                            trackContainerRect = trackContainer[0].getBoundingClientRect();
                            if (scope.verticalSeekBar) {
                                if (!trackContainerRect.height) {
                                    trackFillOrderPositioning = trackContainer[0].scrollHeight;
                                } else {
                                    trackFillOrderPositioning = trackContainerRect.height;
                                }
                            }
                            else {
                                if (!trackContainerRect.width) {
                                    trackFillOrderPositioning = trackContainer[0].scrollWidth;
                                } else {
                                    trackFillOrderPositioning = trackContainerRect.width;
                                }

                            }

                        };

                        var updateKnobPosition = function(percent) {
                            var percentStr = (percent * 100) + '%';
                            if (scope.verticalSeekBar) {
                                knob.css('bottom', percentStr);
                                trackFill.css('height', percentStr);
                            }
                            else {
                                knob.css('left', percentStr);
                                trackFill.css('width', percentStr);
                            }
                        };

                        var modelRenderer = function() {
                            if (isNaN(ngModelCtrl.$viewValue)) {
                                ngModelCtrl.$viewValue = ngModelCtrl.$modelValue || min;
                            }

                            var viewVal = ngModelCtrl.$viewValue;
                            scope.currentModelValue = viewVal;

                            //wait for min, max and step to be set then only update UI to avoid NaN on percent calculation
                            if ((min || min === 0) && max && step) {
                                updateKnobPosition(getValueToPercent(viewVal));
                            }
                        };

                        var setModelValue = function(val) {
                            scope.currentModelValue = getValidMinMax(getValidStep(val));
                            ngModelCtrl.$setViewValue(scope.currentModelValue);
                        };

                        var updateMin = function(val) {
                            min = parseFloat(val);
                            if(isNaN(min)){
                               min = b2bSeekBarConfig.min; 
                            }
                            modelRenderer();
                        };

                        var updateMax = function(val) {
                            max = parseFloat(val);
                            if(isNaN(max)){
                               max = b2bSeekBarConfig.max; 
                            }
                            modelRenderer();
                        };

                        var updateStep = function(val) {
                            step = parseFloat(val);
                            if (!attr['skipInterval']) {
                                skipInterval = step;
                            }
                        };

                        var updateSkipInterval = function(val) {
                            skipInterval = step * Math.ceil(val / (step!==0?step:1));
                        };

                        angular.isDefined(attr.min) ? attr.$observe('min', updateMin) : updateMin(b2bSeekBarConfig.min);
                        angular.isDefined(attr.max) ? attr.$observe('max', updateMax) : updateMax(b2bSeekBarConfig.max);
                        if (angular.isDefined(attr.step)) {
                            attr.$observe('step', updateStep);
                        }
                        if (angular.isDefined(attr.skipInterval)) {
                            attr.$observe('skipInterval', updateSkipInterval);
                        }
                        scope.currentModelValue = getValidMinMax(getValidStep(min));
                        var onMouseDown = function(e) {
                            switch (e.which) {
                                case 1:
                                    // left mouse button
                                    break;
                                case 2:
                                case 3:
                                    // right or middle mouse button
                                    return;
                            }
                            ;

                            scope.isDragging = true;
                            seekBarKnob[0].focus();
                            updateTrackContainerRect();
                            if (attr['onDragInit']) {
                                scope.onDragInit();
                            }
                            events.stopPropagation(e);
                            events.preventDefault(e);
                             scope.$apply(function() {
                                setModelValue(getPercentToValue(getPositionToPercent(e[axisPosition])));
                            });
                        };

                        var onMouseUp = function() {

                            if (attr['onDragEnd']) {
                                scope.onDragEnd();
                            }
                            scope.isDragging = false;
                            scope.$digest();
                        };

                        var onMouseMove = function(e) {
                            if (scope.isDragging) {
                                events.stopPropagation(e);
                                events.preventDefault(e);

                                scope.$apply(function() {
                                    setModelValue(getPercentToValue(getPositionToPercent(e[axisPosition])));
                                });
                            }
                        };

                        function onKeyDown(e) {
                            if (!(e.keyCode)) {
                                e.keyCode = e.which;
                            }
                            var updateStep;
                            switch (e.keyCode) {
                                case keymap.KEY.LEFT:
                                    if (!scope.verticalSeekBar) {
                                        updateStep = -skipInterval;
                                    }
                                    break;
                                case keymap.KEY.RIGHT:
                                    if (!scope.verticalSeekBar) {
                                        updateStep = skipInterval;
                                    }
                                    break;
                                case keymap.KEY.UP:
                                    if (scope.verticalSeekBar) {
                                        updateStep = skipInterval;
                                    }
                                    break;
                                case keymap.KEY.DOWN:
                                    if (scope.verticalSeekBar) {
                                        updateStep = -skipInterval;
                                    }
                                    break;
                                default:
                                    return;
                            }

                            if (updateStep) {
                                events.stopPropagation(e);
                                events.preventDefault(e);
                                scope.$apply(function() {
                                    setModelValue(ngModelCtrl.$viewValue + updateStep);
                                });
                                if (attr['onDragEnd']) {
                                scope.onDragEnd();
                            }
                            }
                        }

                        elm.on('keydown', onKeyDown);
                        elm.on('mousedown', onMouseDown);

                        $documentBind.event('mousemove', 'isDragging', onMouseMove, scope, true, 0);
                        $documentBind.event('mouseup', 'isDragging', onMouseUp, scope, true, 0);

                        ngModelCtrl.$render = function() {
                            if (!scope.isDragging) {
                                modelRenderer();
                            }
                        };
                        ngModelCtrl.$viewChangeListeners.push(modelRenderer);
                        ngModelCtrl.$formatters.push(getValidMinMax);
                        ngModelCtrl.$formatters.push(getValidStep);
                    }
                };
            }]);
/**
 * @ngdoc directive
 * @name Layouts.att:separators
 *
 * @description
 *  <file src="src/separators/docs/readme.md" />
 *
 * @usage

 *
 * @example
 *  <section id="code">   
 <b>HTML + AngularJS</b>
 <example module="b2b.att">
 <file src="src/separators/docs/demo.html" />
 <file src="src/separators/docs/demo.js" />
 </example>
 </section>
 *
 */

angular.module('b2b.att.separators', []);
/**
 * @ngdoc directive
 * @name Buttons, links & UI controls.att:slider
 *
 * @description
 *  <file src="src/slider/docs/readme.md" />
 *
 * @usage
 *  <b2b-slider min="0" max="400" step="1" no-aria-label skip-interval="1" ng-model="horizontalSliderVal" style="width:180px; margin: auto;" on-drag-end="onDragEnd()" on-drag-init="onDragStart()" label-id="slider-label" post-aria-label="postAriaLabel"></b2b-slider>
 *
 * @example
    <section id="code">   
        <b>HTML + AngularJS</b>
        <example module="b2b.att">
            <file src="src/slider/docs/demo.html" />
            <file src="src/slider/docs/demo.js" />
        </example>
    </section>
 */

angular.module('b2b.att.slider', ['b2b.att.utilities'])
        .constant('SliderConfig', {
            'min': 0,
            'max': 100,
            'step': 1,
            'skipInterval': 1
        })
        .directive('b2bSlider', ['$documentBind', 'SliderConfig', 'keymap', '$compile', '$log', function($documentBind, SliderConfig, keymap, $compile, $log) {
                return {
                    restrict: 'AE',
                    replace: true,
                    require: 'ngModel',
                    templateUrl: 'b2bTemplate/slider/slider.html',
                    scope: {
                        onDragEnd: '&?',
                        onDragInit: '&?',
                        trackFillColor: '=?',
                        preAriaLabel: '=?',
                        postAriaLabel: '=?',
                        onRenderEnd: '&?',
                        sliderSnapPoints: '=?',
                        customAriaLabel: '=?',
                        labelId: '@?'
                    },
                    link: function(scope, elm, attr, ngModelCtrl) {
                        scope.isDragging = false;
                        scope.verticalSlider = false;
                        scope.isSliderDisabled = true;
                        var min;
                        var max;
                        var step = SliderConfig.step;
                        var skipInterval = SliderConfig.skipInterval;
                        var knob = angular.element(elm[0].querySelector('.slider-knob-container'));
                        var sliderKnob = angular.element(knob[0].querySelector('.slider-knob'));
                        var trackContainer = angular.element(elm[0].querySelector('.slider-track-container'));
                        var trackFill = angular.element(elm[0].querySelector('.slider-track-fill'));
                        var trackContainerRect = {};
                        var axisPosition = "clientX";
                        var trackFillOrderPositioning;

                        //Forcefully disabling the vertical Slider code.
                        if (angular.isDefined(attr.vertical)) {
                            scope.verticalSlider = true;
                            axisPosition = "clientY";
                        }

                        if (angular.isDefined(scope.noAriaLabel) && scope.noAriaLabel !== '') {
                            $log.warn('no-aria-label has been deprecated. This will be removed in v0.6.0.');
                        }
                        if (angular.isDefined(scope.preAriaLabel) && scope.preAriaLabel !== '') {
                            $log.warn('pre-aria-label has been deprecated. Please use label-id instead. This will be removed in v0.6.0.');
                        }
                        if (angular.isDefined(scope.customAriaLabel) && scope.customAriaLabel !== '') {
                            $log.warn('custom-aria-label has been deprecated. Please use label-id and post-aria-label instead. This will be removed in v0.6.0.');
                        }

                        var binarySearchNearest = function (num, arr) {
                            var mid;
                            var lo = 0;
                            var hi = arr.length - 1;
                            
                            while (hi - lo > 1) {
                                mid = Math.floor((lo + hi) / 2);
                                if (arr[mid] < num) {
                                    lo = mid;
                                } else {
                                    hi = mid;
                                }
                            }
                            if (num - arr[lo] < arr[hi] - num) {
                                return arr[lo];
                            }
                            return arr[hi];
                        };
                        
                        var getValidStep = function(val) {
                            val = parseFloat(val);
                            // in case $modelValue came in string number
                            if (!isNaN(val)) {
                                
                                if(attr['sliderSnapPoints'] && scope.sliderSnapPoints.length > 0) {
                                    val = binarySearchNearest(val, scope.sliderSnapPoints);
                                }
                                else {
                                    val = Math.round((val - min) / step) * step + min;
                                }
                                
                                return Math.round(val * 1000) / 1000;
                            }
                        };

                        var getPositionToPercent = function(x) {
                            if (scope.verticalSlider) {
                                return Math.max(0, Math.min(1, (trackContainerRect.bottom - x) / (trackFillOrderPositioning)));
                            }
                            else {
                                return Math.max(0, Math.min(1, (x - trackContainerRect.left) / (trackFillOrderPositioning)));
                            }
                        };

                        var getPercentToValue = function(percent) {
                            return (min + percent * (max - min));
                        };

                        var getValueToPercent = function(val) {
                            return (val - min) / (max - min);
                        };

                        var getValidMinMax = function(val) {
                            return Math.max(min, Math.min(max, val));
                        };

                        var updateTrackContainerRect = function() {
                            trackContainerRect = trackContainer[0].getBoundingClientRect();
                            if (scope.verticalSlider) {
                                if (!trackContainerRect.height) {
                                    trackFillOrderPositioning = trackContainer[0].scrollHeight;
                                } else {
                                    trackFillOrderPositioning = trackContainerRect.height;
                                }
                            }
                            else {
                                if (!trackContainerRect.width) {
                                    trackFillOrderPositioning = trackContainer[0].scrollWidth;
                                } else {
                                    trackFillOrderPositioning = trackContainerRect.width;
                                }

                            }

                        };

                        var updateKnobPosition = function(percent) {
                            var percentStr = (percent * 100) + '%';
                            if (scope.verticalSlider) {
                                knob.css('bottom', percentStr);
                                trackFill.css('height', percentStr);
                            }
                            else {
                                knob.css('left', percentStr);
                                trackFill.css('width', percentStr);
                            }
                        };

                        var modelRenderer = function() {

                            if(attr['disabled']){
                                return;
                            }

                            if (isNaN(ngModelCtrl.$viewValue)) {
                                ngModelCtrl.$viewValue = ngModelCtrl.$modelValue || min;
                            }

                            var viewVal = ngModelCtrl.$viewValue;
                            scope.currentModelValue = viewVal;

                            //wait for min, max and step to be set then only update UI to avoid NaN on percent calculation
                            if ((min || min === 0) && max && step) {
                                updateKnobPosition(getValueToPercent(viewVal));
                            }
                        };

                        var setModelValue = function(val) {
                            scope.currentModelValue = getValidMinMax(getValidStep(val));
                            ngModelCtrl.$setViewValue(scope.currentModelValue);
                        };

                        var updateMin = function(val) {
                            min = parseFloat(val);
                            if(isNaN(min)){
                               min = SliderConfig.min; 
                            }
                            scope.min = min;
                            modelRenderer();
                        };

                        var updateMax = function(val) {
                            max = parseFloat(val);
                            if(isNaN(max)){
                               max = SliderConfig.max; 
                            }
                            scope.max = max;
                            modelRenderer();
                        };

                        var updateStep = function(val) {
                            step = parseFloat(val);
                            if (!attr['skipInterval']) {
                                skipInterval = step;
                            }
                        };

                        var updateSkipInterval = function(val) {
                            skipInterval = step * Math.ceil(val / (step!==0?step:1));
                        };

                        angular.isDefined(attr.min) ? attr.$observe('min', updateMin) : updateMin(SliderConfig.min);
                        angular.isDefined(attr.max) ? attr.$observe('max', updateMax) : updateMax(SliderConfig.max);
                        if (angular.isDefined(attr.step)) {
                            attr.$observe('step', updateStep);
                        }
                        if (angular.isDefined(attr.skipInterval)) {
                            attr.$observe('skipInterval', updateSkipInterval);
                        }
                        scope.currentModelValue = getValidMinMax(getValidStep(min));
                        var onMouseDown = function(e) {

                            if(attr['disabled']){
                                return;
                            }

                            switch (e.which) {
                                case 1:
                                    // left mouse button
                                    break;
                                case 2:
                                case 3:
                                    // right or middle mouse button
                                    return;
                            }

                            scope.isDragging = true;
                            sliderKnob[0].focus();
                            updateTrackContainerRect();
                            if (attr['onDragInit']) {
                                scope.onDragInit();
                            }
                            e.stopPropagation();
                            e.preventDefault();
                             scope.$apply(function() {
                                if(e.type === "touchmove" || e.type === "touchend"){
                                    setModelValue(getPercentToValue(getPositionToPercent(e.touches[0][axisPosition])));
                                }else{
                                    setModelValue(getPercentToValue(getPositionToPercent(e[axisPosition])));
                                }
                                
                            });
                        };
						
						var onKnobMouseDown = function(e) {

                            if(attr['disabled']){
                                return;
                            }

                            switch (e.which) {
                                case 1:
                                    // left mouse button
                                    break;
                                case 2:
                                case 3:
                                    // right or middle mouse button
                                    return;
                            }

                            scope.isDragging = true;
                            sliderKnob[0].focus();
                            updateTrackContainerRect();
                            if (attr['onDragInit']) {
                                scope.onDragInit();
                            }
                            e.stopPropagation();
                            e.preventDefault();
                             scope.$apply(function() {
                                if(e.type === "touchmove" || e.type === "touchend"){
                                    setModelValue(getPercentToValue(getPositionToPercent(e.touches[0][axisPosition])));
                                }else{
                                    setModelValue(getPercentToValue(getPositionToPercent(e[axisPosition])));
                                }
                                
                            });
                        };
						
                        var onMouseUp = function() {

                            if (attr['onDragEnd']) {
                                scope.onDragEnd();
                            }
                            scope.isDragging = false;
                            scope.$digest();
                        };

                        var onMouseMove = function(e) {
                            if (scope.isDragging) {
                                e.stopPropagation();
                                e.preventDefault();

                                scope.$apply(function() {
                                    if(e.type === "touchmove"){
                                        setModelValue(getPercentToValue(getPositionToPercent(e.touches[0][axisPosition])));
                                    }else{
                                        setModelValue(getPercentToValue(getPositionToPercent(e[axisPosition])));
                                    }
                                });
                            }
                        };

                        function onKeyDown(e) {
                            if (!(e.keyCode)) {
                                e.keyCode = e.which;
                            }
                            var updateStep;
                            switch (e.keyCode) {
                                case keymap.KEY.DOWN:
                                case keymap.KEY.LEFT:
                                    if(attr['sliderSnapPoints'] && scope.sliderSnapPoints.length > 0) {
                                        var currentIndex = scope.sliderSnapPoints.indexOf(ngModelCtrl.$viewValue);
                                        if (currentIndex > 0) {
                                            currentIndex--;
                                        }
                                        updateStep = scope.sliderSnapPoints[currentIndex];
                                    }
                                    else {
                                        updateStep = ngModelCtrl.$viewValue - skipInterval;
                                    }
                                    break;
                                case keymap.KEY.UP:
                                case keymap.KEY.RIGHT:
                                    if(attr['sliderSnapPoints'] && scope.sliderSnapPoints.length > 0) {
                                        var currentIndex = scope.sliderSnapPoints.indexOf(ngModelCtrl.$viewValue);
                                        if (currentIndex < scope.sliderSnapPoints.length-1) {
                                            currentIndex++;
                                        }
                                        updateStep = scope.sliderSnapPoints[currentIndex];
                                    }
                                    else {
                                        updateStep = ngModelCtrl.$viewValue + skipInterval;
                                    }
                                    break;
                                case keymap.KEY.END:
                                    if(attr['sliderSnapPoints'] && scope.sliderSnapPoints.length > 0) {
                                        currentIndex = scope.sliderSnapPoints.length-1;
                                        updateStep = scope.sliderSnapPoints[currentIndex];
                                    } else {
                                        setModelValue(scope.max);
                                    }
                                    e.preventDefault();
                                    e.stopPropagation();
                                    break;
                                case keymap.KEY.HOME:
                                    if(attr['sliderSnapPoints'] && scope.sliderSnapPoints.length > 0) {
                                        currentIndex = 0;
                                        updateStep = scope.sliderSnapPoints[currentIndex];
                                    } else {
                                        setModelValue(scope.min);
                                    }
                                    e.preventDefault();
                                    e.stopPropagation();
                                    break;
                                default:
                                    return;
                            }

                            if (angular.isNumber(updateStep) && !attr['disabled']) {
                                e.stopPropagation();
                                e.preventDefault();
                                scope.$apply(function() {
                                    setModelValue(updateStep);
                                });
                                if (attr['onDragEnd']) {
                                    scope.onDragEnd();
                                }
                            }
                        }

                        scope.calculateLeft = function(snapPoint) {
                            var percentStr = (getValueToPercent(snapPoint) * 100) + '%';
                            return {'left': percentStr};
                        }

                        elm.on('keydown', onKeyDown);
                        elm.on('mousedown', onMouseDown);
						elm.on('click', onMouseUp);
                        elm.on('touchstart', onMouseDown);
						sliderKnob.on('mousedown', onKnobMouseDown);
                        sliderKnob.on('touchstart', onKnobMouseDown);
                        $documentBind.event('mousemove', 'isDragging', onMouseMove, scope, true, 0);
                        $documentBind.event('mouseup', 'isDragging', onMouseUp, scope, true, 0);
                        $documentBind.event('touchmove', 'isDragging', onMouseMove, scope, true, 0);
                        $documentBind.event('touchend', 'isDragging', onMouseUp, scope, true, 0);
                        attr.$observe('disabled', function (disabled) {
                            if (disabled) {
                                sliderKnob.removeAttr('tabindex');
                            } else {
                                sliderKnob.attr('tabindex', '0');
                                disabled = false;
                            }

                            elm.toggleClass("slider-disabled", disabled);

                            scope.isSliderDisabled = disabled;

                            if (angular.isDefined(attr.hideDisabledKnob)) {
                                scope.hideKnob = disabled;
                            }
                        });

                        ngModelCtrl.$render = function() {
                            if (!scope.isDragging) {
                                modelRenderer();
                                if (attr['onRenderEnd'] && !attr['disabled']) {
                                    scope.onRenderEnd({currentModelValue: scope.currentModelValue});
                                }
                            }
                        };
                        ngModelCtrl.$viewChangeListeners.push(modelRenderer);
                        ngModelCtrl.$formatters.push(getValidMinMax);
                        ngModelCtrl.$formatters.push(getValidStep);
                    }
                };
            }]);
/**
 * @ngdoc directive
 * @name Forms.att:spinButton
 *
 * @param {String} spin-button-id - An ID for the input field
 * @param {Integer} min - Minimum value for the input
 * @param {Integer} max - Maximum value for the input
 * @param {Integer} step - Value by which input field increments or decrements on up/down arrow keys
 * @param {Integer} page-step - Value by which input field increments or decrements on page up/down keys
 * @param {boolean} input-model-key - Default value for input field
 * @param {boolean} disabled-flag - A boolean that triggers directive once the min or max value has reached
 *
 * @description
 *  <file src="src/spinButton/docs/readme.md" />
 *
 * @example
 * 	<section id="code">
	    <example module="b2b.att">
            <file src="src/spinButton/docs/demo.html" />
            <file src="src/spinButton/docs/demo.js" />
       </example>
	</section>
 * 
 */
angular.module('b2b.att.spinButton', ['b2b.att.utilities'])
    .constant('b2bSpinButtonConfig', {
        min: 1,
        max: 10,
        step: 1,
        pageStep: 10,
        inputModelKey: 'value',
        disabledFlag: false
    })
    .directive('b2bSpinButton', ['keymap', 'b2bSpinButtonConfig', 'b2bUserAgent', function (keymap, b2bSpinButtonConfig, userAgent) {
        return {
            restrict: 'EA',
            require: '?ngModel',
            transclude: false,
            replace: true,
            scope: {
                min: '=min',
                max: '=max',
                step: '=step',
                pageStep: '=pageStep',
                spinButtonId: '@',
                inputValue: '=ngModel',
                inputModelKey: '@',
                disabledFlag: "=?"
            },
            templateUrl: 'b2bTemplate/spinButton/spinButton.html',
            controller: ['$scope', '$element', '$attrs', function (scope, element, attrs) {

                scope.isMobile = userAgent.isMobile();
                scope.notMobile = userAgent.notMobile();

                scope.min = attrs.min ? scope.min : b2bSpinButtonConfig.min;
                scope.max = attrs.max ? scope.max : b2bSpinButtonConfig.max;
                scope.step = attrs.step ? scope.step : b2bSpinButtonConfig.step;
                scope.pageStep = attrs.pageStep ? scope.pageStep : b2bSpinButtonConfig.pageStep;
                scope.inputModelKey = attrs.inputModelKey ? scope.inputModelKey : b2bSpinButtonConfig.inputModelKey;
                scope.disabledFlag = attrs.disabledFlag ? scope.disabledFlag : b2bSpinButtonConfig.disabledFlag;
                
                if (scope.min < 0) {
                    scope.min = 0;
                }
                if (scope.max > 999) {
                    scope.max = 999;
                }

                scope.isPlusDisabled = function () {
                    return (scope.disabledFlag || scope.inputValue[scope.inputModelKey] >= scope.max);
                };
                scope.isMinusDisabled = function () {
                    return (scope.disabledFlag || scope.inputValue[scope.inputModelKey] <= scope.min);
                };

                scope.getValidateInputValue = function (value) {
                    if (value <= scope.min) {
                        return scope.min;
                    } else if (value >= scope.max) {
                        return scope.max;
                    } else {
                        return value;
                    }
                };

                scope.plus = function () {
                    scope.inputValue[scope.inputModelKey] = scope.getValidateInputValue(parseInt(scope.inputValue[scope.inputModelKey], 10) + scope.step);
                };
                scope.minus = function () {
                    scope.inputValue[scope.inputModelKey] = scope.getValidateInputValue(parseInt(scope.inputValue[scope.inputModelKey], 10) - scope.step);
                };
                scope.pagePlus = function () {
                    scope.inputValue[scope.inputModelKey] = scope.getValidateInputValue(parseInt(scope.inputValue[scope.inputModelKey], 10) + scope.pageStep);
                };
                scope.pageMinus = function () {
                    scope.inputValue[scope.inputModelKey] = scope.getValidateInputValue(parseInt(scope.inputValue[scope.inputModelKey], 10) - scope.pageStep);
                };

            }],
            link: function (scope, elem) {

                if (scope.notMobile) {
                    angular.element(elem).find('input').attr('aria-live', 'off');
                    angular.element(elem).find('input').attr('role', 'spinbutton');
                }

                elem.find('input').bind('keydown', function (e) {
                    if (e.keyCode === keymap.KEY.UP) {
                        scope.plus();
                    } else if (e.keyCode === keymap.KEY.DOWN){
                        scope.minus();
                    } else if (e.keyCode === keymap.KEY.HOME) {
                        scope.inputValue[scope.inputModelKey] = parseInt(scope.min);
                    } else if (e.keyCode === keymap.KEY.END) {
                        scope.inputValue[scope.inputModelKey] = parseInt(scope.max);
                    } else if (e.keyCode === keymap.KEY.PAGE_UP) {
                        scope.pagePlus();
                    } else if (e.keyCode === keymap.KEY.PAGE_DOWN) {
                        scope.pageMinus();
                    }
                    scope.$apply();
                });

                elem.find('input').bind('keyup', function () {
                    if (scope.inputValue[scope.inputModelKey] === null ||
                        scope.inputValue[scope.inputModelKey] === '' ||
                        scope.inputValue[scope.inputModelKey] < scope.min) {
                        scope.inputValue[scope.inputModelKey] = scope.min;
                        scope.$apply();
                    } else if (angular.isUndefined(scope.inputValue[scope.inputModelKey]) || 
                               scope.inputValue[scope.inputModelKey] > scope.max) {
                        scope.inputValue[scope.inputModelKey] = scope.max;
                        scope.$apply();
                    }
                });

                scope.focusInputSpinButton = function (evt) {
                    evt.preventDefault();
                    if (scope.notMobile) {
                        elem[0].querySelector('input').focus();
                    }
                };

            }
        };  
    }]);
/** 
 * @ngdoc directive 
 * @name Template.att:Static Route
 * 
 * @description 
 *  <file src="src/staticRouteTemplate/docs/readme.md" /> 
 * 
 * @example 
 *  <section id="code"> 
        <example module="b2b.att"> 
            <file src="src/staticRouteTemplate/docs/demo.html" /> 
            <file src="src/staticRouteTemplate/docs/demo.js" /> 
       </example> 
    </section>    
 * 
 */
angular.module('b2b.att.staticRouteTemplate', ['b2b.att.utilities'])
  
/**
 * @ngdoc directive
 * @name Progress & usage indicators.att:statusTracker
 *
 * @scope
 * @param {array} statusObject - An array of status objects that accept heading, estimate, description and state
 * @description
 * <file src="src/statusTracker/docs/readme.md" />
 *
 * @usage
 *
<div ng-controller="statusTrackerController">
    <b2b-status-tracker statuses="statusObject"></b2b-status-tracker>
</div>

 * @example
    <section id="code">   
        <example module="b2b.att">
            <file src="src/statusTracker/docs/demo.html" />
            <file src="src/statusTracker/docs/demo.js" />
        </example>
    </section>
 */

angular.module('b2b.att.statusTracker', ['b2b.att.utilities'])
.constant('b2bStatusTrackerConfig', {
    'maxViewItems': 3,
    'icons': {
        'complete': 'icon-controls-approval',
        'current': 'icon-misc-time',
        'pending': 'icon-controls-statusokay',
        'actionRequired': 'icon-securityalerts-alert',
        'notAvailable': 'icon-controls-restricted'
    }
})
.directive('b2bStatusTracker', ['b2bStatusTrackerConfig', function(b2bStatusTrackerConfig) {
        return {
            restrict: 'EA',
            transclude: false,
            replace: true,
            scope:{
                statuses: '='
            },
            templateUrl: function(scope) {
                return 'b2bTemplate/statusTracker/statusTracker.html';
            },
            link: function(scope, element, attr) {
                scope.currentViewIndex = 0;
                scope.b2bStatusTrackerConfig = b2bStatusTrackerConfig;

                scope.nextStatus = function() {
                    if (scope.currentViewIndex+1 <= scope.statuses.length) {
                        scope.currentViewIndex++;
                    }
                };
                scope.previousStatus = function() {
                    if (scope.currentViewIndex-1 >= 0) {
                        scope.currentViewIndex--;
                    }
                };
                scope.isInViewport = function(index) {
                    return (index < scope.currentViewIndex+3 && index >= scope.currentViewIndex);  // && index > scope.currentViewIndex-2
                };

                scope.removeCamelCase = function(str) {
                    return str.replace(/([a-z])([A-Z])/g, '$1 $2').toLowerCase();
                }
            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Progress & usage indicators.att:stepTracker
 *
 * @scope
 * @param {array} stepsItemsObject - An array of step objects
 * @param {Integer} currenIindex - This indicates the current running step
 * @param {Integer} viewportIndex - This is optional. This can used to start the view port rather than 1 item.
 * @description
 * <file src="src/stepTracker/docs/readme.md" />
 *
 * @usage
 *
 * 	<b2b-step-tracker steps-items-object="stepsObject" current-index="currentStepIndex" step-indicator-heading="stepHeading"></b2b-step-tracker>
 *

 * @example
    <section id="code">   
        <b>HTML + AngularJS</b>
        <example module="b2b.att">
            <file src="src/stepTracker/docs/demo.html" />
            <file src="src/stepTracker/docs/demo.js" />
        </example>
    </section>
 */
angular.module('b2b.att.stepTracker', ['b2b.att.utilities'])
	.constant('b2bStepTrackerConfig', {
		'maxViewItems': 5
	})
	.directive('b2bStepTracker', ['b2bStepTrackerConfig', function(b2bStepTrackerConfig) {
		return {
			restrict: 'EA',
			transclude: true,
			scope:{
				stepsItemsObject:"=",
				currentIndex:"=",
                viewportIndex:"=?"
			},
			templateUrl: 'b2bTemplate/stepTracker/stepTracker.html',
			link: function(scope, ele, attr) {
                if (angular.isDefined(scope.viewportIndex)) {
                    scope.currentViewIndex = scope.viewportIndex - 1;   
                }else{
                    scope.currentViewIndex = 0;
                }
			   
			   scope.b2bStepTrackerConfig = b2bStepTrackerConfig;
			   scope.nextStatus = function() {
					if (scope.currentViewIndex+1 <= scope.stepsItemsObject.length) {
						scope.currentViewIndex++;
					}
				};
				scope.previousStatus = function() {
					if (scope.currentViewIndex-1 >= 0) {
						scope.currentViewIndex--;
					}
				};
				scope.isInViewport = function(index) {
					return (index < scope.currentViewIndex+b2bStepTrackerConfig.maxViewItems && index >= scope.currentViewIndex);
				};
			}
		};
	}]);
     
/**
 * @ngdoc directive
 * @name Buttons, links & UI controls.att:switches
 *
 * @description
 *  <file src="src/switches/docs/readme.md" />
 *
 * @usage
 *  
 *  <!-- On / Off Toggle switch -->
 *  <label for="switch1" class="controlled-text-wrap"> This is ON
 *      <input type="checkbox" role="checkbox" b2b-switches id="switch1" ng-model="switch1.value">
 *  </label>
 *
 *  <!-- On / Off Toggle switch and DISABLED -->
 *  <label for="switch2" class="controlled-text-wrap"> This is ON (disabled)
 *      <input type="checkbox" role="checkbox" b2b-switches id="switch2" ng-model="switch1.value" ng-disabled="true" >
 *  </label> 
 *
 *
 * @example
 *  <section id="code">
        <b>HTML + AngularJS</b>
        <example module="b2b.att">
            <file src="src/switches/docs/demo.js" />
            <file src="src/switches/docs/demo.html" />
        </example>
    </section>
 */
angular.module('b2b.att.switches', ['b2b.att.utilities'])
    .directive('b2bSwitches', ['$compile', '$templateCache', 'keymap', 'events', '$parse', '$timeout', function ($compile, $templateCache, keymap, events, $parse, $timeout) {
        return {
            restrict: 'EA',
            require: ['ngModel'],
            scope: {
                options: "=",
                ngModel: "="
            },
            link: function (scope, element, attrs, ctrl) {
                var ngModelController = ctrl[0];
                if(!angular.isDefined(scope.options)){
                    scope.options = {
                        "on":"On",
                        "off":"Off"
                    }
                } 
                element.parent().bind("keydown mousedown", function (e) {
                    if (!attrs.disabled && (e.keyCode === keymap.KEY.ENTER || e.keyCode === keymap.KEY.SPACE || e.type === 'mousedown')) {
                        events.preventDefault(e);
                        ngModelController.$setViewValue(!ngModelController.$viewValue);
                        element.prop("checked", ngModelController.$viewValue);
                        if(ngModelController.$viewValue){
                        	element.addClass("checked");
                        }
                        else{
                        	element.removeClass("checked");
                        }
                        if(ngModelController.$viewValue){
                            angular.element(switchElements[0]).css("left","10%");
                            angular.element(switchElements[1]).css("left",rightOffSet+ "%");
                            angular.element(switchElements[2]).css("right","100%");
                        }
                        
                        if(!ngModelController.$viewValue){
                            angular.element(switchElements[0]).css("left","100%");
                            angular.element(switchElements[1]).css("left",leftOffSet+ "%"); 
                            angular.element(switchElements[2]).css("right","10%");
                        } 
                        $timeout(function(){
                            element.parent().addClass('focused');
                        }, 100);
                    }
                });
                
                element.wrap('<div class="btn-switch">');
                
                var widt = 0;
                if(!angular.isDefined(attrs.typeSpanish) && scope.options != undefined){
            		var offset = scope.options.on.length;
                	if(offset < scope.options.off.length)
                		offset = scope.options.off.length;
                	widt = ((offset) * 10) + 52;
            	}
                if (angular.isDefined(attrs.typeSpanish)) {
                    widt = 80;
                }
            	element.parent().css("width",widt+"px");
            	var widthWithoutCircle = widt - 26;
                var leftOffSet = ((3*100)/widt);
                var rightOffSet = 100- ((28*100)/widt);
            	
                
                if (navigator.userAgent.match(/iphone/i)){
                    element.attr("aria-live", "polite");
                }
                else {
                    element.removeAttr('aria-live');
                }

                var templateSwitch = angular.element($templateCache.get("b2bTemplate/switches/switches.html"));
                if (angular.isDefined(attrs.typeSpanish)) {
                    templateSwitch = angular.element($templateCache.get("b2bTemplate/switches/switches-spanish.html"));
                }

                templateSwitch = $compile(templateSwitch)(scope);
                element.parent().append(templateSwitch);
                
                var switchElements = angular.element(element.parent().children()[1]).children();
                angular.element(switchElements[0]).css("width","100%");
				angular.element(switchElements[2]).css("width","100%");
                
                scope.$watch('ngModel', function () {
                	if(ngModelController.$viewValue){
    					angular.element(switchElements[0]).css("left","10%");
    					angular.element(switchElements[1]).css("left",rightOffSet+ "%");
    					angular.element(switchElements[1]).css("transition","all 0.3s linear 0s");
    					angular.element(switchElements[2]).css("right","100%");
    					element.addClass("checked");
    				}
                    
                    if(!ngModelController.$viewValue){
                    	angular.element(switchElements[0]).css("left","100%");
                    	angular.element(switchElements[1]).css("left",leftOffSet+ "%");
                    	angular.element(switchElements[1]).css("transition","all 0.3s linear 0s");
    					angular.element(switchElements[2]).css("right","10%");
    					element.removeClass("checked");
    				}
                });
                
                element.bind("focus", function (e) {
                    element.parent().addClass('focused');
                });

                element.bind("blur", function (e) {
                    element.parent().removeClass('focused');
                });
            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Buttons, links & UI controls.att:switchesv2
 *
 * @description
 *  <file src="src/switchesv2/docs/readme.md" />
 *
 * @usage
 *  
 *   <div class="form-row">
 *      <label for="{{switch2}}"> 
 *           <span aria-hidden="true">Voicemail is</span>
 *         <div b2b-switches-v2 id="switch2" ng-model="switch4.value" ng-disabled="allDisabled">
 *           </div>
 *       </label>
 *  </div>
 *
 * @example
 *  <section id="code">
        <b>HTML + AngularJS</b>
        <example module="b2b.att">
            <file src="src/switchesv2/docs/demo.js" />
            <file src="src/switchesv2/docs/demo.html" />
        </example>
    </section>

     */
angular.module('b2b.att.switchesv2', ['b2b.att.utilities'])
.directive('b2bSwitchesV2', ['$compile', '$templateCache', 'keymap', 'events', '$parse', '$timeout', function ($compile, $templateCache, keymap, events, $parse, $timeout) {

return {
    restrict: 'EA',
    require: ['ngModel'],
    scope: {
        options: "=",
        ngModelSwitch: '=ngModel', 
        id: '=',
        disabledFlag: '=ngDisabled'
    },
    replace: true,
    transclude: true,
    templateUrl: function(element,attrs){
        return 'b2bTemplate/switches/switches-v2.html'
    },
    link: function (scope, element, attrs, ctrl) { 
        var ngModelController = ctrl[0];

        scope.model;
        scope.switchId = scope.id;

        var externalLabelElement = angular.element(element.parent()[0]).children()[0];
        scope.ourLabel = angular.isDefined(attrs.labelText) ?attrs.labelText: '' ;
        var switchOverlayElement = element.children()[1]; 
        var radioButtons = element.find('input');
        var switchElements = element.find('span');

        angular.element(element.parent()).addClass('btn-swtich-label');
        externalLabelElement.classList.add('b2b-switch-span');
        switchOverlayElement.classList.add('switch-overlay-element');

        var modelValue = false;

        if(!angular.isDefined(scope.options)){
            scope.options = {
                "on":"On",
                "off":"Off"
            } 
        }

        if(scope.ngModelSwitch !== undefined && scope.ngModelSwitch) {
            modelValue = true;
        } else {
            modelValue = false;
        }

        var widt = 0;

        if(!angular.isDefined(attrs.typeSpanish) && scope.options != undefined){
            var offset = scope.options.on.length;
            if(offset < scope.options.off.length)
                offset = scope.options.off.length;
            widt = ((offset) * 10) + 52;
        }
        if (angular.isDefined(attrs.typeSpanish)) {
            widt = 80;
        }

        element.css("width",widt+"px");
        var widthWithoutCircle = widt - 26;
        var leftOffSet = ((3*100)/widt);
        var rightOffSet = 100- ((28*100)/widt);

        angular.forEach(radioButtons, function(el) {
            angular.element(el).bind('focus', function(e) {
                element.addClass('focused');
            });

            angular.element(el).bind('blur', function(e) {
                element.removeClass('focused');
            });
        });
        
        angular.element(switchElements[0]).css("width","100%");
        angular.element(switchElements[2]).css("width","100%");


       if(!modelValue){
            $timeout(function() {
                radioButtons[1].checked = true;
            }, 10);
            angular.element(switchElements[0]).css("left","100%");
            angular.element(switchElements[1]).css("left",leftOffSet+ "%");
            angular.element(switchElements[1]).css("transition","all 0.3s linear 0s");
            angular.element(switchElements[2]).css("right","10%");

        } else {
           $timeout(function() {
                radioButtons[0].checked = true;
            }, 10);
            angular.element(switchElements[0]).css("left","10%");
            angular.element(switchElements[1]).css("left",rightOffSet+ "%");
            angular.element(switchElements[1]).css("transition","all 0.3s linear 0s");
            angular.element(switchElements[1]).addClass("onstate"); 
            angular.element(switchElements[2]).css("right","100%"); 

        }

        var toggleButton = function(){

            modelValue = !modelValue;

            ngModelController.$setViewValue(modelValue);

            if (modelValue){
                scope.ngModelSwitch = true;
                angular.element(switchElements[0]).css("left","10%");
                angular.element(switchElements[1]).css("left",rightOffSet+ "%");
                angular.element(switchElements[1]).css("transition","all 0.3s linear 0s");
                angular.element(switchElements[1]).addClass("onstate");  
                angular.element(switchElements[2]).css("right","100%");

            } else {
                scope.ngModelSwitch = false;
                angular.element(switchElements[0]).css("left","100%");
                angular.element(switchElements[1]).css("left",leftOffSet+ "%");
                angular.element(switchElements[1]).css("transition","all 0.3s linear 0s");
                angular.element(switchElements[1]).removeClass("onstate");  
                angular.element(switchElements[2]).css("right","10%");
            }
        }

        angular.element(externalLabelElement).bind("click", function (e) {
            toggleButton();
        })

        angular.element(radioButtons).bind("keydown", function (e) {
            if ((e.keyCode === keymap.KEY.LEFT || e.keyCode === keymap.KEY.UP || e.keyCode === keymap.KEY.RIGHT || e.keyCode === keymap.KEY.DOWN || e.keyCode === keymap.KEY.SPACE)) {
                toggleButton();
            }
        });
        angular.element(switchOverlayElement).bind("click", function (e) {
            toggleButton();
        })


    }
}
}]);
/** 
 * @ngdoc directive 
 * @name Template.att:Table with Drag and Drop
 * 
 * @description 
 *  <file src="src/tableDragAndDrop/docs/readme.md" /> 
 * 
 * @example 
 *  <section id="code"> 
        <example module="b2b.att"> 
            <file src="src/tableDragAndDrop/docs/demo.html" /> 
            <file src="src/tableDragAndDrop/docs/demo.js" /> 
       </example> 
    </section>    
 * 
 */
angular.module('b2b.att.tableDragAndDrop', ['b2b.att.utilities','b2b.att.tables'])
  
/**
 * @ngdoc directive
 * @name Messages, modals & alerts.att:tableMessages
 *
 * @description
 *  <file src="src/tableMessages/docs/readme.md" />
 *
 * @usage
    <!-- no matching results -->
    <b2b-table-message msg-type="'noMatchingResults'">
       <p>No Matching Results</p>
    </b2b-table-message>
  
    <!-- info could not load -->
    <b2b-table-message msg-type="'infoCouldNotLoad'" on-refresh-click="refreshClicked()">
    </b2b-table-message>
   
    <!-- magnify search -->
    <b2b-table-message msg-type="'magnifySearch'">
    </b2b-table-message>
   
    <!-- loading data -->
    <b2b-table-message msg-type="'loadingTable'">
          <!-- custom html -->
          <p>The data is currently loading...</p>
    </b2b-table-message>

 * @example
    <section id="code">   
        <b>HTML + AngularJS</b>
        <example module="b2b.att">
            <file src="src/tableMessages/docs/demo.html" />
            <file src="src/tableMessages/docs/demo.js" />
        </example>
    </section>
 */
angular.module('b2b.att.tableMessages', [])
    .directive('b2bTableMessage', [function() {
        return {
            restrict: 'AE',
            replace: true,
            transclude: true,
            scope: {
                msgType: '=',
                onRefreshClick: '&'
            },
            templateUrl: 'b2bTemplate/tableMessages/tableMessage.html',
            link: function(scope) {
                scope.refreshAction = function(evt) {
                    scope.onRefreshClick(evt);
                };
            }
        };
    }]);

/**
 * @ngdoc directive
 * @name Tabs, tables & accordions.att:tableScrollbar
 *
 * @description
 *  <file src="src/tableScrollbar/docs/readme.md" />
 *
 * @usage
 * 
<b2b-table-scrollbar>
    <table>
        <thead type="header">
            <tr>
                <th role="columnheader" scope="col" key="Id" id="col1">Id</th>
                .....
            </tr>
        </thead>
        <tbody type="body">
            <tr>
                <td id="rowheader0" headers="col1">1002</td>
                .....
            </tr>
        </tbody>
    </table>
</b2b-table-scrollbar>
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/tableScrollbar/docs/demo.html" />
            <file src="src/tableScrollbar/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.tableScrollbar', [])
    .directive('b2bTableScrollbar', ['$timeout', function ($timeout) {
        return {
            restrict: 'E',
            scope: true,
            transclude: true,
            templateUrl: 'b2bTemplate/tableScrollbar/tableScrollbar.html',
            link: function (scope, element, attrs, ctrl) {
                var firstThWidth, firstTdWidth, firstColumnWidth, firstColumnHeight, trHeight = 0;
                var pxToScroll = '';
                var tableElement = element.find('table');
                var thElements = element.find('th');
                var tdElements = element.find('td');
                var innerContainer = angular.element(element[0].querySelector('.b2b-table-inner-container'));
                var outerContainer = angular.element(element[0].querySelector('.b2b-table-scrollbar'));

                scope.disableLeft = true;
                scope.disableRight = false;

                if (angular.isDefined(thElements[0])) {
                    firstThWidth = thElements[0].offsetWidth;
                }
                if (angular.isDefined(tdElements[0])) {
                    firstTdWidth = tdElements[0].offsetWidth;
                }
                firstColumnWidth = (firstThWidth > firstTdWidth) ? firstThWidth : firstTdWidth;

                innerContainer.css({
                    'padding-left': (firstColumnWidth + 2) + 'px'
                });

                angular.forEach(element.find('tr'), function (eachTr, index) {
                    trObject = angular.element(eachTr);
                    firstColumn = angular.element(trObject.children()[0]);

                    angular.element(firstColumn).css({
                        'left': '0px',
                        'width': (firstColumnWidth + 2) + 'px',
                        'position': 'absolute'
                    });

                    trHeight = trObject[0].offsetHeight;
                    firstColumnHeight = firstColumn[0].offsetHeight;
                    if (navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
                        firstColumnHeight += 1;
                    }

                    if (trHeight !== firstColumnHeight - 1) {
                        if (trHeight > firstColumnHeight) {
                            if (navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
                                trHeight -= 1;
                            }
                            angular.element(firstColumn).css({
                                'height': (trHeight + 1) + 'px'
                            });
                        } else {
                            angular.element(trObject).css({
                                'height': (firstColumnHeight - 1) + 'px'
                            });
                        }
                    }

                });

                pxToScroll = outerContainer[0].offsetWidth - firstColumnWidth;

                scope.scrollLeft = function () {
                    innerContainer[0].scrollLeft = innerContainer[0].scrollLeft + 20 - pxToScroll;
                };

                scope.scrollRight = function () {
                    innerContainer[0].scrollLeft = innerContainer[0].scrollLeft + pxToScroll - 20;
                };

                scope.checkScrollArrows = function () {
                    if (innerContainer[0].scrollLeft == 0) {
                        scope.disableLeft = true;
                    } else {
                        scope.disableLeft = false;
                    }

                    if (((innerContainer[0].offsetWidth - firstColumnWidth) + innerContainer[0].scrollLeft) >= tableElement[0].offsetWidth) {
                        scope.disableRight = true;
                    } else {
                        scope.disableRight = false;
                    }
                };


                innerContainer.bind('scroll', function () {
                    $timeout(function () {
                        scope.checkScrollArrows();
                    }, 1);
                });

            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Tabs, tables & accordions.att:tables
 *
 * @description
 *  <file src="src/tables/docs/readme.md" />
 *
 * @usage
 *
 Table
 <table b2b-table table-data="tableData" search-string="searchString">
    <thead b2b-table-row type="header">
        <tr>
            <th b2b-table-header key="requestId" default-sort="a" id="col1">Header 1</th>
            <th b2b-table-header key="requestType" sortable="false" id="col2">Header 2</th>
        </tr>
    </thead>
    <tbody b2b-table-row type="body" row-repeat="rowData in tableData">
        <tr>
            <td b2b-table-body id="rowheader{{$index}}" headers="col1" ng-bind="rowData['requestId']"> </td>
            <td b2b-table-body ng-bind="rowData['requestType']"></td>
        </tr>
    </tbody>
 </table>
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/tables/docs/demo.html" />
            <file src="src/tables/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.tables', ['b2b.att.utilities'])
    .constant('b2bTableConfig', {
        defaultSortPattern: false, // true for descending & false for ascending
        highlightSearchStringClass: 'tablesorter-search-highlight',
        zebraStripCutOff: 6, // > zebraStripCutOff
        tableBreakpoints: [ // breakpoints are >= min and < max
            {
                min: 0,
                max: 480,
                columns: 2
            },
            {
                min: 480,
                max: 768,
                columns: 3
            },
            {
                min: 768,
                max: 1025,
                columns: 5
            },
            {
                min: 1025,
                max: 2050,
                columns: 7
            }
        ]
    })
    .directive('b2bTable', ['$filter', '$window', 'b2bTableConfig', '$timeout', function ($filter, $window, b2bTableConfig, $timeout) {
        return {
            restrict: 'EA',
            replace: true,
            transclude: true,
            scope: {
                tableData: "=",
                viewPerPage: "=",
                currentPage: "=",
                totalPage: "=",
                searchCategory: "=",
                searchString: "=",
                nextSort: '=',
                isOpen: '='
            },
            require: 'b2bTable',
            templateUrl: 'b2bTemplate/tables/b2bTable.html',
            controller: ['$scope', '$attrs', function ($scope, $attrs) {
                this.headers = [];
                this.currentSortIndex = null;
                this.openResponsiveColumns = $scope.isOpen;
                this.responsive = $scope.responsive = $attrs.responsive;
                this.customHtml = $scope.customHtml = $attrs.customHtml;
                this.maxTableColumns = -1;
                this.totalTableColums = 0;
                this.active = $scope.active = false;
                this.responsiveRowScopes = [];
                this.hideColumnPriority = [];
                this.hiddenColumn = [];
                this.setIndex = function (headerScope, priority) {
                    this.headers.push(headerScope);
                    if (this.responsive) {
                        this.totalTableColums++;
                        if (!isNaN(priority)) {
                            this.hideColumnPriority[priority] = this.totalTableColums - 1;
                        } else {
                            this.hideColumnPriority[this.totalTableColums - 1] = this.totalTableColums - 1;
                        }
                    }
                    return this.totalTableColums - 1;
                };
                this.getIndex = function (headerName) {
                    for (var i = 0; i < this.headers.length; i++) {
                        if (this.headers[i].headerName === headerName) {
                            return this.headers[i].index;
                        }
                    }
                    return null;
                };
                this.setResponsiveRow = function (responsiveRowScope) {
                    this.responsiveRowScopes.push(responsiveRowScope);
                }
                $scope.nextSort = '';
                this.sortData = function (columnIndex, reverse, externalSort) {
                    if ($scope.$parent && $scope.$parent !== undefined) {
                        $scope.$parent.columnIndex = columnIndex;
                        $scope.$parent.reverse = reverse;
                    }
                    this.currentSortIndex = columnIndex;
                    if (externalSort === true) {
                        if (!reverse) {
                            $scope.nextSort = 'd'
                        } else {
                            $scope.nextSort = 'a'
                        }
                    }
                    $scope.currentPage = 1;
                    this.resetSortPattern();
                };
                this.getSearchString = function () {
                    return $scope.searchString;
                };
                this.resetSortPattern = function () {
                    for (var i = 0; i < this.headers.length; i++) {
                        var currentScope = this.headers[i];
                        if (currentScope.index !== this.currentSortIndex) {
                            currentScope.resetSortPattern();
                        }
                    }
                };

                $scope.$watch('nextSort', function (val) {
                    if ($scope.$parent && $scope.$parent !== undefined) {
                        $scope.$parent.nextSort = val;
                    }

                });
            }],
            link: function (scope, elem, attr, ctrl) {
                scope.searchCriteria = {};
                scope.tableBreakpoints = attr.tableConfig ? scope.$parent.$eval(attr.tableConfig) : angular.copy(b2bTableConfig.tableBreakpoints);
                scope.$watchCollection('tableData', function (value) {
                    if (value && !isNaN(value.length)) {
                        scope.totalRows = value.length;
                    }
                });
                scope.$watch('currentPage', function (val) {
                    if (scope.$parent && scope.$parent !== undefined) {
                        scope.$parent.currentPage = val;
                    }

                });
                scope.$watch('viewPerPage', function (val) {
                    if (scope.$parent && scope.$parent !== undefined) {
                        scope.$parent.viewPerPage = val;
                    }
                });
                scope.$watch('totalRows', function (val) {
                    if (scope.$parent && scope.$parent !== undefined) {
                        if (val > b2bTableConfig.zebraStripCutOff) {
                            scope.$parent.zebraStripFlag = true;
                        } else {
                            scope.$parent.zebraStripFlag = false;
                        }
                    }
                });
                scope.$watch(function () {
                    return scope.totalRows / scope.viewPerPage;
                }, function (value) {
                    if (!isNaN(value)) {
                        scope.totalPage = Math.ceil(value);
                        scope.currentPage = 1;
                    }
                });
                var searchValCheck = function (val) {
                    if (angular.isDefined(val) && val !== null && val !== "") {
                        return true;
                    }
                };
                var setSearchCriteria = function (v1, v2) {
                    if (searchValCheck(v1) && searchValCheck(v2)) {
                        var index = ctrl.getIndex(v2);
                        scope.searchCriteria = {};
                        if (index !== null) {
                            scope.searchCriteria[index] = v1;
                        }
                    } else if (searchValCheck(v1) && (!angular.isDefined(v2) || v2 === null || v2 === "")) {
                        scope.searchCriteria = {
                            $: v1
                        };
                    } else {
                        scope.searchCriteria = {};
                    }
                };
                scope.$watch('searchCategory', function (newVal, oldVal) {
                    if (newVal !== oldVal) {
                        setSearchCriteria(scope.searchString, newVal);
                    }
                });
                scope.$watch('searchString', function (newVal, oldVal) {
                    if (newVal !== oldVal) {
                        setSearchCriteria(newVal, scope.searchCategory);
                    }
                });
                scope.$watchCollection('searchCriteria', function (val) {
                    if (scope.$parent && scope.$parent !== undefined) {
                        scope.$parent.searchCriteria = val;
                    }
                    scope.totalRows = scope.tableData && ($filter('filter')(scope.tableData, val, false)).length || 0;
                    scope.currentPage = 1;
                });
                var window = angular.element($window);
                var findMaxTableColumns = function () {
                    var windowWidth;
                    windowWidth = $window.innerWidth;
                    ctrl.maxTableColumns = -1;
                    for (var i in scope.tableBreakpoints) {
                        if (windowWidth >= scope.tableBreakpoints[i].min && windowWidth < scope.tableBreakpoints[i].max) {
                            ctrl.maxTableColumns = scope.tableBreakpoints[i].columns;
                            break;
                        }
                    }
                    if (ctrl.maxTableColumns > -1 && ctrl.totalTableColums > ctrl.maxTableColumns) {
                        ctrl.active = true;
                    } else {
                        ctrl.active = false;
                    }
                    for (var i in ctrl.responsiveRowScopes) {
                        if(angular.isFunction(ctrl.responsiveRowScopes[i].setActive) ){
                            ctrl.responsiveRowScopes[i].setActive(ctrl.active);
                        }
                    }
                };
                var findHiddenColumn = function () {
                    var columnDiffenence = ctrl.maxTableColumns > -1 ? ctrl.totalTableColums - ctrl.maxTableColumns : 0;
                    ctrl.hiddenColumn = [];
                    if (columnDiffenence > 0) {
                        var tempHideColumnPriority = angular.copy(ctrl.hideColumnPriority);
                        for (var i = 0; i < columnDiffenence; i++) {
                            ctrl.hiddenColumn.push(tempHideColumnPriority.pop());
                        }
                    }
                };
                var resizeListener = function () {
                    findMaxTableColumns();
                    findHiddenColumn();
                };
                if (ctrl.responsive) {
                    window.bind('resize', function () {
                        resizeListener();
                        scope.$apply();
                    });
                    $timeout(function () {
                        resizeListener();
                    }, 100);
                }
            }
        };
    }])
    .directive('b2bTableRow', [function () {
        return {
            restrict: 'EA',
            compile: function (elem, attr) {
                if (attr.type === 'header') {
                    angular.noop();
                } else if (attr.type === 'body') {
                    var html = elem.children();
                    if (attr.rowRepeat) {
                        html.attr('ng-repeat', attr.rowRepeat.concat(" | orderBy : (reverse?'-':'')+ columnIndex  | filter : searchCriteria : false "));
                    }
                    html.attr('ng-class', "{'odd': $odd && zebraStripFlag}");
                    html.attr('b2b-responsive-row', '{{$index}}');
                    html.attr('class', 'data-row');
                   html.attr('is-open',attr.isOpen);
                   elem.append(html);
                }
            }
        };
    }])
    .directive('b2bTableHeader', ['b2bTableConfig', function (b2bTableConfig) {
        return {
            restrict: 'EA',
            replace: true,
            transclude: true,
            scope: {
                sortable: '@',
                defaultSort: '@',
                index: '@key'
            },
            require: '^b2bTable',
            templateUrl: function (elem, attr) {
                if (attr.sortable === 'false') {
                    return 'b2bTemplate/tables/b2bTableHeaderUnsortable.html';
                } else {
                    return 'b2bTemplate/tables/b2bTableHeaderSortable.html';
                }
            },
            link: function (scope, elem, attr, ctrl) {
                var reverse = b2bTableConfig.defaultSortPattern;
                scope.headerName = elem.text();
                scope.headerId = elem.attr('id');
                scope.sortPattern = null;
                var priority = parseInt(attr.priority, 10);
                scope.columnIndex = ctrl.setIndex(scope, priority);

                scope.isHidden = function () {
                    return (ctrl.hiddenColumn.indexOf(scope.columnIndex) > -1);
                };

                scope.$watch(function () {
                    return elem.text();
                }, function (value) {
                    scope.headerName = value;
                });
                scope.sort = function (sortType) {
                    if (typeof sortType === 'boolean') {
                        reverse = sortType;
                    }
                    ctrl.sortData(scope.index, reverse, false);
                    scope.sortPattern = reverse ? 'descending' : 'ascending';
                    reverse = !reverse;
                };
                scope.$watch(function () {
                    return ctrl.currentSortIndex;
                }, function (value) {
                    if (value !== scope.index) {
                        scope.sortPattern = null;
                    }
                });

                if (scope.sortable === undefined || scope.sortable === 'true' || scope.sortable === true) {
                    scope.sortable = 'true';
                } else if (scope.sortable === false || scope.sortable === 'false') {
                    scope.sortable = 'false';
                }

                if (scope.sortable !== 'false') {
                    if (scope.defaultSort === 'A' || scope.defaultSort === 'a') {
                        scope.sort(false);
                    } else if (scope.defaultSort === 'D' || scope.defaultSort === 'd') {
                        scope.sort(true);
                    }
                }
                scope.resetSortPattern = function () {
                    reverse = b2bTableConfig.defaultSortPattern;
                };
            }
        };
    }])
    .directive('b2bResponsiveRow', ['$templateCache', '$timeout', '$compile', function ($templateCache, $timeout, $compile) {
        return {
            restrict: 'EA',
            require: '^b2bTable',
            controller: ['$scope', function ($scope) {
                this.rowValues = $scope.rowValues = [];
                $scope.headerClass = {};
                $scope.bodyClass = {};
                this.setRowValues = function (rowValue)
                {this.rowValues.push(rowValue);};
                var columnIndexCounter = - 1;
                this.getIndex = function ()
                {   columnIndexCounter++;
                    return columnIndexCounter;};
                this.setHeaderClass = function (columnIndex, classNames)
                {   $scope.headerClass[columnIndex] = classNames;};
                this.setBodyClass = function (columnIndex, classNames)
                {   $scope.bodyClass[columnIndex] = classNames;};
            }],
            link: function (scope, elem, attr, ctrl) {
                if (ctrl.responsive) {
                    scope.rowIndex = attr.b2bResponsiveRow;
                    scope.active = false;
                    scope.expandFlag = ctrl.openResponsiveColumns;
                    scope.headerValues = ctrl.headers;
                    ctrl.setResponsiveRow(scope); 
                    var firstTd = elem.find('td').eq(0);
                    if (scope.expandFlag) {
                        elem.addClass('opened');
                    }
                    scope.setActive = function (activeFlag) {
                        scope.active = activeFlag;
                        if (scope.active) {
                            elem.addClass('has-button');
                            firstTd.attr('role', 'rowheader');
                            firstTd.parent().attr('role', 'row');
                        } else {
                            elem.removeClass('has-button');
                            firstTd.removeAttr('role');
                            firstTd.parent().removeAttr('role');
                        }
                    };
                    scope.toggleExpandFlag = function (expandFlag) {
                        if (angular.isDefined(expandFlag)) {
                            scope.expandFlag = expandFlag;
                        } else {
                            scope.expandFlag = !scope.expandFlag;
                        }
                        if (scope.expandFlag) {
                            elem.addClass('opened');
                        } else {
                            elem.removeClass('opened');
                        }
                    };

                    firstTd.attr('scope', 'row');
                    scope.$on('$destroy', function () {
                        elem.next().remove();
                    });
                    $timeout(function () {
                        /*if(ctrl.customHtml!=="true"){
                            scope.firstTdId = firstTd.attr('id');
                            var firstTdContent = firstTd.html();
                            var toggleButtonTemplate = '<span ng-show="!active">' + firstTdContent + '</span><a  aria-describedby="sup-actNum{{$id}}" aria-expanded="{{expandFlag}}" ng-show="active" ng-click="toggleExpandFlag()"><i ng-class="{\'icon-controls-add-maximize\': !expandFlag, \'icon-close\': expandFlag}"  aria-hidden="true"></i><span class="cell-button-text" b2b-accessibility-click="13,32" tabindex = "0">' + firstTdContent + '</span></a><span id="sup-actNum{{$id}}" style="display:none">{{expandFlag && "Hide row below." || "Show row below."}}</span>';
                            toggleButtonTemplate = $compile(toggleButtonTemplate)(scope);
                            firstTd.html('');
                            firstTd.prepend(toggleButtonTemplate);
                        }*/
                        var template = $templateCache.get('b2bTemplate/tables/b2bResponsiveRow.html');
                        template = $compile(template)(scope);
                        elem.after(template);
                    }, 100);
                }
            }
        };
    }])
    .directive('b2bResponsiveList', ['$templateCache', '$timeout', '$compile', function ($templateCache, $timeout, $compile) {
        return {
            restrict: 'EA',
            require: '^b2bTable',
            link: function (scope, elem, attr, ctrl) {
                scope.columnIndex = parseInt(attr.b2bResponsiveList, 10);
                scope.isVisible = function () {
                    return (ctrl.hiddenColumn.indexOf(scope.columnIndex) > -1);
                };
            }
        };
    }])
    .directive('b2bTableBody', ['$filter', '$timeout', 'b2bTableConfig', function ($filter, $timeout, b2bTableConfig) {
        return {
            restrict: 'EA',
            require: ['^b2bTable', '?^b2bResponsiveRow'],
            scope: true,
            replace: true,
            transclude: true,
            templateUrl: 'b2bTemplate/tables/b2bTableBody.html',
            link: function (scope, elem, attr, ctrl) {
                var b2bTableCtrl = ctrl[0];
                var b2bResponsiveRowCtrl = ctrl[1];
                var highlightSearchStringClass = b2bTableConfig.highlightSearchStringClass;
                var searchString = "";
                var responsiveTableHeaderClass = attr.b2bResponsiveTableHeaderClass || '';
                var responsiveTableBodyClass = attr.b2bResponsiveTableBodyClass || '';
                var wrapElement = function (elem) {
                    var text = elem.text();
                    elem.html($filter('b2bHighlight')(text, searchString, highlightSearchStringClass));
                };
                var traverse = function (elem) {
                    var innerHtml = elem.children();
                    if (innerHtml.length > 0) {
                        for (var i = 0; i < innerHtml.length; i++) {
                            traverse(innerHtml.eq(i));
                        }
                    } else {
                        wrapElement(elem);
                        return;
                    }
                };
                var clearWrap = function (elem) {
                    var elems = elem.find('*');
                    for (var i = 0; i < elems.length; i++) {
                        if (elems.eq(i).attr('class') && elems.eq(i).attr('class').indexOf(highlightSearchStringClass) !== -1) {
                            var text = elems.eq(i).text();
                            elems.eq(i).replaceWith(text);
                        }
                    }
                };
                if (b2bResponsiveRowCtrl) {
                    scope.columnIndex = b2bResponsiveRowCtrl.getIndex();
                    scope.isHidden = function () {
                        return (b2bTableCtrl.hiddenColumn.indexOf(scope.columnIndex) > -1);
                    };
                }
                $timeout(function () {
                    var actualHtml = elem.children();
                    scope.$watch(function () {
                        return b2bTableCtrl.getSearchString();
                    }, function (val) {
                        searchString = val;
                        clearWrap(elem);
                        if (actualHtml.length > 0) {
                            traverse(elem);
                        } else {
                            wrapElement(elem);
                        }
                    });
                    if (b2bResponsiveRowCtrl) {
                        b2bResponsiveRowCtrl.setRowValues(elem.html());
                    }
                }, 50);
            }
        };
    }])
    .directive('b2bTableSort', ['b2bTableConfig','$timeout', function (b2bTableConfig,$timeout) {
        return {
            restrict: 'EA',
            replace: true,
            require: '^b2bTable',
            link: function (scope, elem, attr, ctrl) {
                var initialSort = '',
                    nextSort = '',
                    tempsort = '';
                initialSort = attr.initialSort;

                scope.sortTable = function (msg,trigger) {
                    if(trigger == 'dropdown'){
                        if(nextSort === '') {
                          ctrl.sortData(msg, false, false);
                        }
                        else if (nextSort === 'd' || nextSort === 'D') {
                         ctrl.sortData(msg, false, false);
                        }else{
                         ctrl.sortData(msg, true, false);
                        }
                        return;
                    }
                    $timeout(function(){
                        if (nextSort.length > 0) {

                        if (nextSort === 'd' || nextSort === 'D') {
                            tempsort = nextSort
                            ctrl.sortData(msg, true, true);
                            nextSort = 'a';
                             $timeout(function(){
                                if(!angular.isUndefined(elem[0].querySelector('.sortButton')) || elem[0].querySelector('.sortButton') !== null ){
                                    angular.element(elem[0].querySelector('.sortButton'))[0].focus();
                                }
                            },100);

                        } else {
                            tempsort = nextSort
                            ctrl.sortData(msg, false, true);
                            nextSort = 'd';
                             $timeout(function(){
                                if(!angular.isUndefined(elem[0].querySelector('.sortButton')) || elem[0].querySelector('.sortButton') !== null ){
                                    angular.element(elem[0].querySelector('.sortButton'))[0].focus();
                                }
                            },100);
                        }
                    } else if (initialSort.length > 0) {

                        if (initialSort === 'd' || initialSort === 'D') {
                            tempsort = nextSort
                            ctrl.sortData(msg, true, true);
                            nextSort = 'a';
                            $timeout(function(){
                                if(!angular.isUndefined(elem[0].querySelector('.sortButton')) || elem[0].querySelector('.sortButton') !== null ){
                                    angular.element(elem[0].querySelector('.sortButton'))[0].focus();
                                }
                            },100);

                        } else {
                            tempsort = nextSort
                            ctrl.sortData(msg, false, true);
                            nextSort = 'd';
                             $timeout(function(){
                                if(!angular.isUndefined(elem[0].querySelector('.sortButton')) || elem[0].querySelector('.sortButton') !== null ){
                                    angular.element(elem[0].querySelector('.sortButton'))[0].focus();
                                }
                            },100);


                        }
                    }
                    },10)

                };

                scope.sortDropdown = function(msg) {

                    if(tempsort==='') {

                        tempsort='a'
                    }
                    if(tempsort === 'd' || tempsort === 'D' ) {
                        ctrl.sortData(msg, true, false);
                    } else {
                       ctrl.sortData(msg, false, false);
                    }

                };
            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Tabs, tables & accordions.att:tabs
 *
 * @description
 *  <file src="src/tabs/docs/readme.md" />
 *
 * @usage
 *  <b2b-tabset tab-id-selected="activeTabsId">
        <b2b-tab ng-repeat="tab in gTabs" tab-item="tab" 
                 id="{{tab.uniqueId}}" aria-controls="{{tab.tabPanelId}}"
                 ng-disabled="tab.disabled">
            {{tab.title}}
        </b2b-tab>
    </b2b-tabset>
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/tabs/docs/demo.html" />
            <file src="src/tabs/docs/demo.js" />
        </example>
    </section>
 *
 */

angular.module('b2b.att.tabs', ['b2b.att.utilities'])
    .directive('b2bTabset', function () {
        return {
            restrict: 'EA',
            transclude: true,
            replace: true,
            scope: {
                tabIdSelected: '='
            },
            templateUrl: 'b2bTemplate/tabs/b2bTabset.html',
            controller: ['$scope', function ($scope) {

                this.setTabIdSelected = function (tab) {
                    $scope.tabIdSelected = tab.id;
                };

                this.getTabIdSelected = function () {
                    return $scope.tabIdSelected;
                };
            }]
        };
    })
    .directive('b2bTab', ['keymap', function (keymap) {
        return {
            restrict: 'EA',
            transclude: true,
            replace: true,
            require: '^b2bTabset',
            scope: {
                tabItem: "="
            },
            templateUrl: 'b2bTemplate/tabs/b2bTab.html',
            controller: [function(){}],
            link: function (scope, element, attr, b2bTabsetCtrl) {

                if (scope.tabItem && !scope.tabItem.disabled) {
                    scope.tabItem.disabled = false;
                }

                scope.isTabActive = function () {
                    return (scope.tabItem.id === b2bTabsetCtrl.getTabIdSelected());
                };

                scope.clickTab = function () {
                    if (attr.disabled) {
                        return;
                    }
                    b2bTabsetCtrl.setTabIdSelected(scope.tabItem);
                };

                scope.nextKey = function () {
                    var el = angular.element(element[0])[0];
                    var elementToFocus = null;
                    while (el && el.nextElementSibling) {
                        el = el.nextElementSibling;
                        if (!el.querySelector('a').disabled) {
                            elementToFocus = el.querySelector('a');
                            break;
                        }
                    }

                    if (!elementToFocus) {
                        var childTabs = element.parent().children();
                        for (var i = 0; i < childTabs.length; i++) {
                            if (!childTabs[i].querySelector('a').disabled) {
                                elementToFocus = childTabs[i].querySelector('a');
                                break;
                            }
                        }
                    }

                    if (elementToFocus) {
                        elementToFocus.focus();
                    }
                };

                scope.previousKey = function () {
                    var el = angular.element(element[0])[0];
                    var elementToFocus = null;

                    while (el && el.previousElementSibling) {
                        el = el.previousElementSibling;
                        if (!el.querySelector('a').disabled) {
                            elementToFocus = el.querySelector('a');
                            break;
                        }
                    }

                    if (!elementToFocus) {
                        var childTabs = element.parent().children();
                        for (var i = childTabs.length - 1; i > 0; i--) {
                            if (!childTabs[i].querySelector('a').disabled) {
                                elementToFocus = childTabs[i].querySelector('a');
                                break;
                            }
                        }
                    }

                    if (elementToFocus) {
                        elementToFocus.focus();
                    }
                };

                angular.element(element[0].querySelector('a')).bind('keydown', function (evt) {

                    if (!(evt.keyCode)) {
                        evt.keyCode = evt.which;
                    }

                    switch (evt.keyCode) {
                        case keymap.KEY.RIGHT:
                            evt.preventDefault();
                            scope.nextKey();
                            break;

                        case keymap.KEY.LEFT:
                            evt.preventDefault();
                            scope.previousKey();
                            break;

                        default:;
                    }
                });
            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Messages, modals & alerts.att:tagBadges
 *
 * @description
 *  <file src="src/tagBadges/docs/readme.md" />
 *
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/tagBadges/docs/demo.html" />
            <file src="src/tagBadges/docs/demo.js" />
        </example>
    </section>
 *
 */
angular.module('b2b.att.tagBadges', ['b2b.att.utilities'])
        .directive('b2bTagBadge',['$timeout',function($timeout){
            return{
                restrict: 'EA',
                link: function(scope,elem,attr,ctrl){
                    elem.addClass('b2b-tags');
                    if(angular.element(elem[0].querySelector('.icon-close')).length>0) {
                        var item = angular.element(elem[0].querySelector('.icon-close'));
                        item.bind('click',function(){
                        elem.css({'height':'0','width':'0','padding':'0','border':'0'});
                        elem.attr('tabindex','0');
                        elem[0].focus();
                        item.parent().remove();
                        elem[0].bind('blur',function(){
                            elem[0].remove();
                        });
                    });  
                    }
                  



                }
            };   
}]);
/**
 * @ngdoc directive
 * @name Forms.att:textArea
 *
 * @description
 *  <file src="src/textArea/docs/readme.md" />
 *
 * @usage
 *  <textarea b2b-reset b2b-reset-textarea ng-model="textAreaModel" ng-disabled="disabled" ng-trim="false" placeholder="{{placeholderText}}" rows="{{textAreaRows}}" maxlength="{{textAreaMaxlength}}" role="textarea"></textarea>
 *
 * @example
    <section id="code">
        <b>HTML + AngularJS</b>
        <example module="b2b.att">
            <file src="src/textArea/docs/demo.html" />
            <file src="src/textArea/docs/demo.js" />
        </example>
    </section>
 */
angular.module('b2b.att.textArea', ['b2b.att.utilities'])

.directive('b2bResetTextarea', [ function () {
    return {
        restrict: 'A',
        require: 'b2bReset',
        link: function (scope, element, attrs, ctrl) {

            var resetButton = ctrl.getResetButton();
            
            var computeScrollbarAndAddClass = function () {
                if (element.prop('scrollHeight') > element[0].clientHeight) {
                    element.addClass('hasScrollbar');
                } else {
                    element.removeClass('hasScrollbar');
                }
            };
            
            computeScrollbarAndAddClass();

            element.on('focus keyup', function(){
                computeScrollbarAndAddClass();
            });
        }
    };
}]);

/**
 * @ngdoc directive
 * @name Forms.att:timeInputField
 *
 * @description
 *  <file src="src/timeInputField/docs/readme.md" />
 *
 
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/timeInputField/docs/demo.html" />
            <file src="src/timeInputField/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.timeInputField',['ngMessages', 'b2b.att.utilities']).directive('b2bTimeFormat',function(){
    return{
        restrict : 'A',
        require : '^ngModel',
        link : function(scope,elem,attr,ctrl){
            elem.on('keyup',function(evt){
                var modelValue = ctrl.$modelValue;
                var format = attr.b2bTimeFormat;
                 modelValue = modelValue.split(':');
                if(format == "12"){
                    if(!(modelValue[0] <= 12 && modelValue[0] > 0 ) || !(modelValue[1] <= 59)){
                        ctrl.$setValidity('inValidTime',false);   
                    }else{
                        ctrl.$setValidity('inValidTime',true);
                    }
                }else if(format =="24"){
                    if(!(modelValue[0] <= 23) || !(modelValue[1] <= 59)){
                        ctrl.$setValidity('inValidTime',false);
                    }else{
                        ctrl.$setValidity('inValidTime',true);
                    }
                }                
               scope.$apply();
            });
        }
    }
});

/**
 * @ngdoc directive
 * @name Forms.att:tooltipsForForms
 *
 * @description
 *  <file src="src/tooltipsForForms/docs/readme.md" />
 *
 * @example
 <example module="b2b.att">
 <file src="src/tooltipsForForms/docs/demo.html" />
 <file src="src/tooltipsForForms/docs/demo.js" />
 </example>
 */
angular.module('b2b.att.tooltipsForForms', ['b2b.att.utilities'])
        .directive('b2bTooltip', ['$document', '$window', '$isElement', function ($document, $window, $isElement) {
                return  {
                    restrict: 'A',
                    link: function (scope, elem, attr, ctrl) {
                        var icon = elem[0].querySelector('.tooltip-element');
                        var btnIcon = elem[0].querySelector('.btn.tooltip-element');
                        var tooltipText = elem[0].querySelector('.helpertext');
                        var tooltipWrapper = elem[0].querySelector('.tooltip-size-control');
                        if (elem.hasClass('tooltip-onfocus')) {
                            var inputElm = angular.element(elem[0].querySelector("input"));
                            var textAreaElm = angular.element(elem[0].querySelector("textarea"));
                        }
                        
                        angular.element(icon).attr({'aria-expanded': false});
                        angular.element(btnIcon).attr({'aria-expanded': false});
                        var calcTooltip = function () {
                            if (!elem.hasClass('tooltip active')) {
                                if (elem.hasClass('tooltip-onfocus')) {
                                    angular.element(elem[0].querySelector("input")).triggerHandler('focusout');
                                }
                                if (elem.hasClass('tooltip-onclick')) {
                                    return false;
                                }
                                angular.element(icon).removeClass('active');
                                angular.element(icon).attr({'aria-expanded': true});
                                angular.element(icon).attr({'aria-describedby': angular.element(tooltipText).attr('id')});
                                angular.element(tooltipText).attr({'aria-hidden': false});
                                elem.addClass('active');

                                var tooltipIconPos = angular.element(icon).prop('offsetLeft'),
                                        tooltipPosition = angular.element(tooltipText).prop('offsetWidth') / 2,
                                        tipOffset = (tooltipIconPos - 30) - tooltipPosition,
                                        maxRightPos = (($window.innerWidth - 72) - (tooltipPosition * 2)) - 14.5;

                                if ($window.innerWidth >= '768') {
                                    if (tipOffset < 0) {// if icon on far left side of page
                                        tipOffset = 15;
                                    }
                                    else if (tooltipIconPos > maxRightPos) {// if icon is far right side of page
                                        tipOffset = maxRightPos;
                                    }
                                    else {// if tooltip in the middle somewhere
                                        tipOffset = tipOffset;
                                    }
                                    angular.element(tooltipWrapper).css({left: tipOffset + 'px'});
                                }
                            }
                        };
                        
                        // TOOLTIP LINK ONCLICK AND FOCUS
                        angular.element(icon).on('click mouseover mouseout focus blur', function (e) {
                            if (e.type == 'mouseover') {
                                calcTooltip();
                            }
                            else if (e.type == 'mouseout' && elem.hasClass('active')) {
                                if (!elem.hasClass('activeClick')) {
                                    angular.element(tooltipText).attr({
                                        'aria-hidden': true,
                                        'tabindex': '-1'
                                    });
                                    elem.removeClass('active');
                                } else if (elem.hasClass('activeClick') && navigator.userAgent.match(/iphone/i)) {
                                    elem.removeClass('active activeClick');
                                }
                            }

                            else {
                                if (elem.hasClass('activeClick')) {
                                    angular.element(icon).attr({'aria-expanded': false});
                                    angular.element(tooltipText).attr({'aria-hidden': true});
                                    angular.element(icon).removeAttr('aria-describedby');
                                    elem.removeClass('activeClick active');
                                    e.preventDefault();
                                }
                                else if (e.type == 'click') {
                                    elem.addClass('activeClick');
                                    calcTooltip();
                                    e.preventDefault();
                                }
                                else {
                                    angular.element(icon).on('keydown', function (e) {
                                        if (e.keyCode == '32') {
                                            (elem.hasClass('active')) ? elem.removeClass('active') : elem.addClass('value');
                                            angular.element(icon).triggerHandler('click');
                                            e.preventDefault();
                                        } else if (e.keyCode == '27') {
                                            (elem.hasClass('active')) ? elem.removeClass('active activeClick') : elem.addClass('value');
                                        }
                                    });
                                    e.preventDefault();
                                }
                            }
                            e.preventDefault();
                        });

                        // TOOLTIP BUTTON INSIDE A TEXT FIELD
                        angular.element(btnIcon).on('click', function (e) {
                            var $this = angular.element(this);
                            if ($this.hasClass('active') && elem.hasClass('tooltip-onclick')) {
                                elem.removeClass('active');
                                $this.removeClass('active');
                                angular.element(tooltipText).removeAttr('aria-live');
                                $this.attr({'aria-expanded': 'false'});
                                $this.removeAttr('aria-describedby');
                            } else {
                                elem.addClass('active');
                                $this.addClass('active');
                                $this.attr({'aria-expanded': 'true', 'aria-describedby': angular.element(tooltipText).attr('id')});
                                angular.element(tooltipText).attr({'aria-live': 'polite'});
                            }
                        });

                        angular.element(btnIcon).on('blur', function (e) {
                            var $this = angular.element(this);
                            if ($this.hasClass('active') && elem.hasClass('tooltip-onclick')) {
                                elem.removeClass('active');
                                $this.removeClass('active');
                                angular.element(tooltipText).removeAttr('aria-live');
                                $this.attr({'aria-expanded': 'false'});
                                $this.removeAttr('aria-describedby');
                            }
                        });  

                        angular.element(btnIcon).on('keydown', function (e) {
                            var $this = angular.element(this);
                            if (e.keyCode == '27') {
                                var $this = angular.element(this);
                                if ($this.hasClass('active') && elem.hasClass('tooltip-onclick')) {
                                    elem.removeClass('active');
                                    $this.removeClass('active');
                                    angular.element(tooltipText).removeAttr('aria-live');
                                    $this.attr({'aria-expanded': 'false'});
                                    $this.removeAttr('aria-describedby');
                                }
                            }
                        });

                        // close all tooltips if clicking something else
                        $document.bind('click', function (e) {
                            var isElement = $isElement(angular.element(e.target), elem, $document);
                            if (!isElement) {
                                elem.removeClass('active');
                                angular.element(elem[0].querySelector('.tooltip-element')).removeClass('active');
                                angular.element(tooltipText).removeAttr('aria-live');
                                angular.element(elem[0].querySelector('.tooltip-element')).attr({'aria-expanded': 'false'});
                                angular.element(elem[0].querySelector('.tooltip-element')).removeAttr('aria-describedby');
                            };
                        });

                        angular.element(inputElm).on('keydown', function (e) {
                            if (e.keyCode == '27'){
                                elem.removeClass('active');
                                angular.element(tooltipText).css('display', 'none');
                                angular.element(tooltipText).removeAttr('aria-live');

                                if (angular.element(this).attr('aria-describedby') === undefined){

                                }

                                else if ((spaceIndex = angular.element(this).attr('aria-describedby').lastIndexOf(' ')) >= 0){

                                    var describedByValue = angular.element(this).attr('aria-describedby').slice(0, spaceIndex);

                                    angular.element(this).attr('aria-describedby', describedByValue);

                                }
                                else {
                                    angular.element(this).removeAttr('aria-describedby');
                                }
                            }
                        });

                        angular.element(textAreaElm).on('keydown', function (e) {
                            if (e.keyCode == '27'){
                                elem.removeClass('active');
                                angular.element(tooltipText).css('display', 'none');
                                angular.element(tooltipText).removeAttr('aria-live');
                                if (angular.element(this).attr('aria-describedby') === undefined){

                                }

                                else if ((spaceIndex = angular.element(this).attr('aria-describedby').lastIndexOf(' ')) >= 0){

                                    var describedByValue = angular.element(this).attr('aria-describedby').slice(0, spaceIndex);

                                    angular.element(this).attr('aria-describedby', describedByValue);

                                }
                                else {
                                    angular.element(this).removeAttr('aria-describedby');
                                }
                            }
                        });

                        // TOOLTIP TRIGGERED AUTOMATICALLY INSIDE A TEXT FIELD
                        angular.element(inputElm).on('focus', function (e) {
                            var allTooltip = $document[0].querySelectorAll('[class*="tooltip"]');
                            for (var i = 0; i < allTooltip.length; i++) {
                                if (angular.element(allTooltip[i]).hasClass('active')) {
                                    angular.element(allTooltip[i]).triggerHandler('click');
                                }
                            };
                            angular.element(this).attr({'aria-describedby': angular.element(tooltipText).attr('id')});
                            angular.element(tooltipText).css('display', 'block');
                            angular.element(tooltipText).attr({'aria-live': 'polite'});
                            elem.addClass('active');
                        });
                        angular.element(inputElm).on('blur', function (e) {
                            elem.removeClass('active');
                            angular.element(tooltipText).css('display', 'none');
                            angular.element(tooltipText).removeAttr('aria-live');
                            angular.element(this).removeAttr('aria-describedby');
                        });

                        // TOOLTIP TRIGGERED AUTOMATICALLY INSIDE A TEXTAREA
                        angular.element(textAreaElm).on('focus', function (e) {
                            var allTooltip = $document[0].querySelectorAll('[class*="tooltip"]');
                            for (var i = 0; i < allTooltip.length; i++) {
                                if (angular.element(allTooltip[i]).hasClass('active')) {
                                    angular.element(allTooltip[i]).triggerHandler('click');
                                }
                            };
                            elem.addClass('active');
                            angular.element(tooltipText).css('display', 'block');
                            angular.element(tooltipText).attr({'aria-live': 'polite'});
                            angular.element(this).attr({'aria-describedby': angular.element(tooltipText).attr('id')});
                        });
                        angular.element(textAreaElm).on('blur', function (e) {
                            elem.removeClass('active');
                            angular.element(tooltipText).css('display', 'none');
                            angular.element(tooltipText).removeAttr('aria-live');
                            angular.element(this).removeAttr('aria-describedby');
                        });
                        
                        //TOOLTIP TRIGGERED AUTOMATICALLY INSIDE A element with trigger focus
                        if(elem.attr('trigger') == "focus"){
                            angular.element(icon).on('focus', function (e) {
                                calcTooltip();
                            });
                            angular.element(icon).on('blur', function (e) {
                                if (elem.hasClass('active')) {
                                    if (!elem.hasClass('activeClick')) {
                                        angular.element(tooltipText).attr({
                                            'aria-hidden': true,
                                            'tabindex': '-1'
                                        });
                                        elem.removeClass('active');
                                    } else if (elem.hasClass('activeClick') && navigator.userAgent.match(/iphone/i)) {
                                        elem.removeClass('active activeClick');
                                    }
                                }
                            });
                        }
                    }
                };
            }]); 
/**
 * @ngdoc directive
 * @name Navigation.att:TreeNavigation
 *
 *
 * @scope
 * @param {String} setRole - This value needs to be "tree". This is required to incorporate CATO requirements.
 * @param {Boolean} groupIt - This value needs to be "false" for top-level tree rendered.
 *
 * @description
 *  <file src="src/treeNav/docs/readme.md" />
 *
 * @usage
 *      <div class="b2b-tree">
 *                <b2b-tree-nav collection="treeStructure" set-role="tree" group-it="false"></b2b-tree-nav>
 *            </div>
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/treeNav/docs/demo.html" />
            <file src="src/treeNav/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.treeNav', ['b2b.att.utilities'])
    .directive('b2bTreeNav', function () {
        return {
            restrict: "E",
            replace: true,
            scope: {
                collection: '=',
                groupIt: '=',
                setRole: '@'
            },
            templateUrl: function (element, attrs) {
                if (attrs.groupIt === 'true') {
                    return "b2bTemplate/treeNav/groupedTree.html";
                } else {
                    return "b2bTemplate/treeNav/ungroupedTree.html";
                }
            },
            link: function (scope) {               
                if (!(scope.setRole === 'tree')) {
                    scope.setRole = 'group';
                }             
            }
        }
    })
    .directive('b2bMember', ['$compile', '$timeout', 'keymap', function ($compile, $timeout, keymap) {
        return {
            restrict: "E",
            replace: true,
            scope: {
                member: '=',
                groupIt: '='
            },
            templateUrl: 'b2bTemplate/treeNav/treeMember.html',
            link: function (scope, element, attrs) {
                scope.elemArr = [];
                var removeRootTabIndex = function (elem) {
                    if (elem.parent().parent().eq(0).hasClass('b2b-tree')) {
                        elem.attr('tabindex', -1);                        
                        return;
                    }
                    removeRootTabIndex(elem.parent());
                };
				scope.$watch('member.child', function(newVal, oldVal){					
					if(newVal !== oldVal){
						scope.showChild();
					};
				});
                scope.showChild = function () {
                        if (!element.hasClass('grouped')) {
                            if (angular.isArray(scope.member.child) && scope.member.child.length > 0 && (scope.member.divide === undefined || scope.member.child.length < scope.member.divide)) {
                                scope.groupIt = false;
                                element.addClass('grouped');
                                element.append("<b2b-tree-nav collection='member.child' group-it='" + scope.groupIt + "'></b2b-tree-nav>");
                                $compile(element.contents())(scope);
                                if(scope.member.active && scope.member.active === true){
                                    element.find('i').eq(0).removeClass('icon-collapsed');
                                };
                                if(scope.member.selected && scope.member.selected === true){
                                    element.attr('aria-selected', true);
                                    element.attr('tabindex', 0);
                                    removeRootTabIndex(element);
                                };
                                if(scope.member.active && scope.member.active == undefined){
                                    element.find('i').eq(0).addClass('icon-collapsed');
                                };
                            } else if (scope.member.child && scope.member.divide && scope.member.child.length > scope.member.divide) {
                                element.addClass('grouped');
                                scope.groupIt = true;
                                // FILTER - GROUPBY - APPROACH 
                                var j = 0;
                                var grpName = '';
                                if(scope.member.child[0].groupName !== undefined){
                                    grpName = scope.member.child[0].groupName;
                                }
                                else{
                                    var toSlice = scope.member.child[0].name.search(' ');
                                    grpName = scope.member.child[0].name.slice(0, toSlice);
                                }

                                for (i = 0; i < scope.member.child.length; i += scope.member.divide) {
                                    j = 0;
                                    for (j = j + i; j < (i + scope.member.divide); j++) {                                        
                                        if (j === scope.member.child.length) {
                                            scope.member.child[j - 1].grpChild = grpName + ' ' + (i + 1) + ' - ' + (scope.member.child.length);
                                            break;
                                            
                                            if(scope.member.child[j-1].active && scope.member.child[j-1].active===true){
                                                scope.member.child[j-1].activeGrp = true;
                                            };
                                            
                                        }
                                        if (i + scope.member.divide > scope.member.child.length) {
                                            scope.member.child[j].grpChild = grpName + ' ' + (i + 1) + ' - ' + (scope.member.child.length);
                                            if(scope.member.child[j].active && scope.member.child[j].active===true){
                                                scope.member.child[j].activeGrp = true;
                                            };

                                        } else {
                                            scope.member.child[j].grpChild = grpName + ' ' + (i + 1) + ' - ' + (i + scope.member.divide);
                                            if(scope.member.child[j].active && scope.member.child[j].active===true){
                                                scope.member.child[j].activeGrp = true;
                                            };
                                        }
                                    }
                                }
								if(scope.member.divide){
									element.append("<b2b-tree-nav collection='member.child' group-it='" + scope.groupIt + "'></b2b-tree-nav>");
								} else {
									element.append("<b2b-tree-nav collection='member.child' group-it='" + scope.groupIt + "'></b2b-tree-nav>");
								}
                                $compile(element.contents())(scope);
                                if(scope.member.active && scope.member.active === true){
                                    element.find('i').eq(0).removeClass('icon-collapsed');
                                };
                                if(scope.member.selected && scope.member.selected === true){
                                    element.attr('aria-selected', true);
                                };
                                if( scope.member.active && scope.member.active == undefined){
                                    element.find('i').eq(0).addClass('icon-collapsed');
                                };
                            }
                        }
                };
				//Below condition opens node for opening on json load.
                if(scope.member.active && scope.member.active == true){
                    scope.showChild();
                };
                if(scope.member.active == undefined && !element.find('a').eq(0).hasClass('active') && scope.member.child !== undefined){
                    element.find('i').eq(0).addClass('icon-collapsed');
                }
                else if(scope.member.child == undefined){
                    element.find('i').eq(0).addClass('icon-circle');
                };
                element.bind('keydown', function (evt) {
                    switch (evt.keyCode) {
                        case keymap.KEY.ENTER:
                            if (element.hasClass('bg') && scope.member.onSelect !== undefined) {
                                scope.member.onSelect(scope.member);
                            }
                            evt.stopPropagation();
                            break;
                        default: 
                            break;                            
                    }
                    
                });
                //else getting true in every case .. so better use switch case .. that makes more sense you dumb.
                element.bind('click', function (evt) {
					scope.showChild();
					var expandFunc = scope.member.onExpand;
					
                    //onSelect
                        if (element.hasClass('bg') && scope.member.onSelect !== undefined) {
                                    scope.member.onSelect(scope.member);
                                }
                        if (element.find('a').eq(0).hasClass('active') && scope.member.onExpand !== undefined) {
                           var eValue = scope.member.onExpand(scope.member);
                        }
                        if (!element.find('a').eq(0).hasClass('active') && scope.member.onCollapse !== undefined) {
                            scope.member.onCollapse(scope.member);
                        }
                });
            }
        }
}])
    .directive('b2bTreeLink', ['keymap', '$timeout', function (keymap, $timeout) {
        return {
            restrict: 'A',
            link: function (scope, element, attr, ctrl) {
                var rootE, parentE, upE, downE;
                var closeOthersUp = function (elem,isKeyPress,passiveClose) {
                    //For accordion functionality on sibling nodes
                    if (elem.find('a').eq(0).hasClass('active')) {
                        activeToggle(elem,isKeyPress,passiveClose);
                        return;
                    }
                    if (elem.hasClass('bg') && !isKeyPress) {
                        elem.removeClass('bg');
                        if (elem.attr('aria-selected')) {
                            elem.attr('aria-selected', 'false');
                        }                        
                    }
                    if (elem[0].previousElementSibling !== null) {
                        closeOthersUp(angular.element(elem[0].previousElementSibling),isKeyPress);
                    }
                };
                var closeOthersDown = function (elem,isKeyPress,passiveClose) {
                    //For accordion functionality on sibling nodes
                    if (elem.find('a').eq(0).hasClass('active')) {
                        activeToggle(elem,isKeyPress,passiveClose);
                        return;
                    }
                    if (elem.hasClass('bg') && !isKeyPress) {
                        elem.removeClass('bg');
                        if (elem.attr('aria-selected')) {
                            elem.attr('aria-selected', 'false');
                        }                        
                    }
                    if (elem[0].nextElementSibling !== null) {
                        closeOthersDown(angular.element(elem[0].nextElementSibling),isKeyPress);
                    }
                };

               
                var removeBackground = function(elem){

                    if(elem.hasClass('b2b-tree')){
                        angular.element(elem[0].getElementsByClassName('bg')).removeClass('bg');
                        return;
                    }else{
                        removeBackground(elem.parent().parent());
                    }

                };

/**
* These two functions used for setting heights on parent nodes as the child node closes
* Retaining this code for future reference

                var addParentHeight = function(e, h) {
                    var parentLi = e.parent().parent();
                    var parentUl = e.parent();
                    if(!parentLi.hasClass('b2b-tree')) {
                        var addHeight = parentUl[0].offsetHeight + h;
                        parentLi.find('ul').eq(0).css({
                            height: addHeight+'px'
                        })
                        addParentHeight(parentLi, h);
                    }                    
                };

                var removeParentHeight = function(e, h) {
                    var parentLi = e.parent().parent();
                    var parentUl = e.parent();
                    if(!parentLi.hasClass('b2b-tree')) {
                        var addHeight = parentUl[0].offsetHeight - h;
                        parentLi.find('ul').eq(0).css({
                            height: addHeight+'px'
                        })
                        removeParentHeight(parentLi, h);
                    }
                };
*/          

            // isKeyPress - to notify that the function is called by Right Key press
            // passiveClose -  prevents firing of oncollapse events during the action
            // of expand function(check the function definition)

                var activeToggle = function (elem,isKeyPress,passiveClose) {
                    var element = elem.find('a').eq(0);
                    if (element.hasClass('active')) {
                        if(!isKeyPress){
                            elem.removeClass('bg');
                        }
                        
                        if (elem.attr('aria-selected') && !isKeyPress) {
                            elem.attr('aria-selected', 'false');
                        }
                        if (!element.find('i').eq(0).hasClass('icon-circle')) {
                            if(isKeyPress && scope.member){
                                if (scope.member.onCollapse !== undefined && !passiveClose) {
                                    scope.member.onCollapse(scope.member);
                                }
                            }
                            element.removeClass('active');
                            elem.attr('aria-expanded', 'false');
                            element.find('i').eq(0).removeClass('icon-expanded');
                            element.find('i').eq(0).addClass('icon-collapsed');
                            //For Animation: below commented code is used to manually set height of UL to zero 
                            //retaining code for future reference
                            /*
                            var totalHeight = elem.find('ul')[0].scrollHeight;
                            removeParentHeight(elem, totalHeight);
                            elem.find('ul').eq(0).css({
                                height: null
                            });*/
                        }
                    } else {
                        if(!isKeyPress){
                            elem.addClass('bg');
                            elem.attr('aria-selected', 'true');
                        }
                        
                        if (!element.find('i').eq(0).hasClass('icon-circle')) {
                            if(isKeyPress){
                                if(typeof scope.showChild === 'function' ){
                                scope.showChild();
                                }
                                if(scope.member){
                                    if (scope.member.onExpand !== undefined) {
                                        scope.member.onExpand(scope.member);
                                    }
                                }
                            }
                            element.addClass('active');
                            elem.attr('aria-expanded', 'true');
                            element.find('i').eq(0).removeClass('icon-collapsed');
                            element.find('i').eq(0).addClass('icon-expanded');
                            //For Animation: below commented code is used to manually set height of the ul generatedon the click of parent LI.
                            //retaining code for future reference
                            /*                            
                            var totalHeight = elem.find('ul')[0].scrollHeight;
                            addParentHeight(elem, totalHeight);
                            elem.find('ul').eq(0).css({
                                height: totalHeight+'px'
                            });*/
                            
                        }
                    }
                };
                element.bind('click', function (evt) {
                    //first we close others and then we open the clicked element
					if (element[0].previousElementSibling) {
						closeOthersUp(angular.element(element[0].previousElementSibling));
					}
					if (element[0].nextElementSibling) {
						closeOthersDown(angular.element(element[0].nextElementSibling));
					}
                    removeBackground(element);
					activeToggle(element);                    
                    
                    evt.stopPropagation();                    
                });
                //default root tree element tabindex set zero
                if (element.parent().parent().hasClass('b2b-tree') && (element.parent()[0].previousElementSibling === null)) {
                    element.attr('tabindex', 0);
                }
                //check root via class
                var isRoot = function (elem) {
                    if (elem.parent().parent().eq(0).hasClass('b2b-tree')) {
                        return true;
                    } else {
                        return false;
                    }
                };
                var findRoot = function (elem) {
                    if (isRoot(elem)) {
                        rootE = elem;
                        return;
                    }
                    findRoot(elem.parent());
                };

                var findPreActive = function (elem) {

                    if (!(elem.hasClass("active"))) {
                        return;
                    } else {
                        var childElems = angular.element(elem[0].nextElementSibling.children);
                        lastE = angular.element(childElems[childElems.length - 1]);
                        if (lastE.find('a').eq(0).hasClass('active')) {
                            findPreActive(lastE.find('a').eq(0));
                        }
                        upE = lastE;
                    }
                };

                var findUp = function (elem) {
                    if (isRoot(elem)) {
                        upE = elem;
                        return;
                    }
                    if (elem[0].previousElementSibling !== null && !angular.element(elem[0].previousElementSibling).hasClass('tree-hide')) {
                        upE = angular.element(elem[0].previousElementSibling);
                        if (upE.find('a').eq(0).hasClass('active')) {
                            findPreActive(upE.find('a').eq(0));
                        }
                    } else {
                        upE = elem.parent().parent();
                    }
                };

                var downElement = function (elem) {
                    if (elem.next().hasClass('tree-hide')) {
                        downElement(elem.next());
                    } else {
                        downE = elem.next();
                    }
                }
                var isBottomElem = false;
                var downParent = function(liElem){
                    if(liElem.eq(0).parent().parent().eq(0).hasClass('b2b-tree')){
                        isBottomElem = true;
                        return;
                    }
                    if(liElem.next().length !== 0){
                        downE = liElem.next().eq(0);
                        return;
                    }
                    else {
                        downParent(liElem.parent().parent());
                    }
                }
                
                var findDown = function (elem) {
                    if (isRoot(elem.parent()) && !elem.hasClass('active')) {
                        downE = elem.parent();
                        return;
                    }
                    if (elem.hasClass('active')) {
                        downE = elem.next().find('li').eq(0);
                        if (downE.hasClass('tree-hide')) {
                            downElement(downE);
                        }

                    } else {
                        downParent(elem.parent());
                        if(isBottomElem === true){
                            downE = elem.parent();
                            isBottomElem = false;
                        }
                    }
                };


                var resetTabPosition = function(element){
                    findRoot(element);
                    angular.element(rootE.parent().parent()[0].querySelector("li[tabindex='0']")).attr('tabindex','-1');
                    var elemToFocus =  rootE.parent().parent()[0].querySelector(".bg")|| rootE;

                    angular.element(elemToFocus).attr('tabindex','0');
                };
                // Function to control the expansion of nodes when the user tabs into the tree and
                // the slected node is not visible
                var expand = function(elemArr){
                    var elem= elemArr.pop();
                    var element = elem.find('a').eq(0);                    
                    var selectedNode = elem.parent().parent()[0].querySelector(".bg");
                    if(selectedNode != null){
                        while(elem){
                             element = elem.find('a').eq(0);
                    if(!element.hasClass('active') ){


                    if (elem[0].previousElementSibling) {
                        closeOthersUp(angular.element(elem[0].previousElementSibling),true,true);
                        }
                        if (elem[0].nextElementSibling) {
                            closeOthersDown(angular.element(elem[0].nextElementSibling),true,true);
                        }

                         if (!element.find('i').eq(0).hasClass('icon-circle')) {
                            if(typeof scope.showChild === 'function' ){
                                scope.showChild();
                            }
                            element.addClass('active');
                            elem.attr('aria-expanded', 'true');
                            element.find('i').eq(0).removeClass('icon-collapsed');
                            element.find('i').eq(0).addClass('icon-expanded');
                            }
                          
                          }   
                          elem = elemArr.pop();
                        }                      
                        
                    }else{
                        return;
                    }                   
                };

                element.find('a').eq(0).bind('mouseenter', function (evt) {
                    angular.forEach(document.querySelectorAll('.activeTooltip'), function(value, key) {
                        angular.element(value).removeClass('activeTooltip') 
                    });
                    element.addClass('activeTooltip');
                });
                element.find('a').eq(0).bind('mouseleave', function (evt) {
                    element.removeClass('activeTooltip');
                });
                element.bind('focus', function (evt) {
                    angular.forEach(document.querySelectorAll('.activeTooltip'), function(value, key) {
                        angular.element(value).removeClass('activeTooltip') 
                    });
                    element.addClass('activeTooltip');
                });
                element.bind('blur', function (evt) {
                    element.removeClass('activeTooltip');
                });
                element.bind('keydown', function (evt) {
                    switch (evt.keyCode) {
                    case keymap.KEY.HOME:
                        evt.preventDefault();
                        evt.stopPropagation();
                        element.attr('tabindex', -1);
                        findRoot(element);
                        rootE.eq(0).attr('tabindex', 0);
                        rootE[0].focus();
                        break;
                    case keymap.KEY.LEFT:
                        evt.preventDefault();
                        evt.stopPropagation(); 
                      
                        if(element.find('a').eq(0).hasClass('active')){
                            if (element[0].previousElementSibling) {
                                closeOthersUp(angular.element(element[0].previousElementSibling),true);
                            }
                            if (element[0].nextElementSibling) {
                                closeOthersDown(angular.element(element[0].nextElementSibling),true);
                             }
                             activeToggle(element,true);
                                return;
                        }
                            element.attr('tabindex', -1);
                            parentE = element.parent().parent();
                            parentE.attr('tabindex', 0);
                            parentE[0].focus();
                        break;
                    case keymap.KEY.UP:
                        evt.preventDefault();
                        evt.stopPropagation();
                        element.attr('tabindex', -1);
                        findUp(element);
                        upE.eq(0).attr('tabindex', 0);
                        upE[0].focus();
                        break;
                    case keymap.KEY.RIGHT:
                        evt.preventDefault();
                        evt.stopPropagation();
                        if(element.find('i').eq(0).hasClass('icon-circle')){
                            break;
                        }    
                        if (!element.find('a').eq(0).hasClass('active')) {
                            if (element[0].previousElementSibling) {
                        closeOthersUp(angular.element(element[0].previousElementSibling),true);
                        }
                        if (element[0].nextElementSibling) {
                            closeOthersDown(angular.element(element[0].nextElementSibling),true);
                        }
                        activeToggle(element,true);
                    
                        }
                        else {
                            element.attr('tabindex', -1);
                            findDown(element.find('a').eq(0));
                            downE.eq(0).attr('tabindex', 0);
                            downE[0].focus();                            
                        }                        
                        break;
                    case keymap.KEY.DOWN:
                        evt.preventDefault();
                        element.attr('tabindex', -1);
                        findDown(element.find('a').eq(0));
                        downE.eq(0).attr('tabindex', 0);
                        downE[0].focus();
                        evt.stopPropagation();
                        break;
                    case keymap.KEY.ENTER:
                        var isSelectedElem = element.hasClass('bg');
                        var enterFunc = function(element){
                            if (isSelectedElem) {
                                element.removeClass('bg');
                                if (element.attr('aria-selected')) {
                                    element.attr('aria-selected', 'false');
                                }                        
                            }
                            else {
                                element.addClass('bg');
                                element.attr('aria-selected', 'true');                                   
                            }  
                        };                            
                        if (element[0].previousElementSibling) {
                            closeOthersUp(angular.element(element[0].previousElementSibling));
                        }
                        if (element[0].nextElementSibling) {
                            closeOthersDown(angular.element(element[0].nextElementSibling));
                        }                   
                        
                        removeBackground(element);
                        enterFunc(element);
                        evt.stopPropagation();                                                      
                        break;
                    case keymap.KEY.TAB:
                        $timeout(function(){
                            resetTabPosition(element);
                        },0);
                         evt.stopPropagation(); 
                        
                        break;
                    default:
                        break;
                    }
                });
            element.bind('keyup',function(evt){
                if(evt.keyCode === keymap.KEY.TAB){
                  
                        var tempElem = element;
                        var elemArr = [];
                        while(!tempElem.hasClass('b2b-tree')){
                            elemArr.push(tempElem);
                            tempElem = tempElem.parent().parent();
                        }
                        elemArr.push(tempElem);
                      
                        expand(elemArr);                    
                }
                 evt.stopPropagation(); 
            });
            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Navigation.att:Tree nodes with checkboxes
 *
 * @param {String} setRole - The value needs to be "tree". This is required to incorporate CATO requirements.
 * @param {boolean} groupIt - The value needs to be "false" for top-level tree rendered. 
 * @param {Object} collection -  The JSON object of tree to be rendered.
 * @description
 *  <file src="src/treeNodeCheckbox/docs/readme.md" />
 *
 * @usage
 *      <div class="b2b-tree-checkbox">
 *                <b2b-tree-node-checkbox collection="treeStructure" set-role="tree" group-it="false"></b2b-tree-node-checkbox>
 *            </div>
 * @example
 *  <section id="code">
        <example module="b2b.att">
            <file src="src/treeNodeCheckbox/docs/demo.html" />
            <file src="src/treeNodeCheckbox/docs/demo.js" />
       </example>
    </section>
 *
 */
angular.module('b2b.att.treeNodeCheckbox', ['b2b.att.utilities'])
    .directive('b2bTreeNodeCheckbox', function () {
        return {
            restrict: "E",
            replace: true,
            scope: {
                collection: '=',
                groupIt: '=',
                setRole: '@'
            },
            templateUrl: function (element, attrs) {
                if (attrs.groupIt === 'true') {
                    return "b2bTemplate/treeNodeCheckbox/groupedTree.html";
                } else {
                    return "b2bTemplate/treeNodeCheckbox/ungroupedTree.html";
                }
            },
            link: function (scope) {
                if (!(scope.setRole === 'tree')) {
                    scope.setRole = 'group';
                }
            }
        }
    })
    .directive('b2bTreeMember', ['$compile', '$timeout', 'keymap', function ($compile, $timeout, keymap) {
        return {
            restrict: "E",
            replace: true,
            scope: {
                member: '=',
                groupIt: '='
            },
            templateUrl: 'b2bTemplate/treeNodeCheckbox/treeMember.html',
            link: function (scope, element, attrs) {
                scope.elemArr = [];
                var removeRootTabIndex = function (elem) {
                    if (elem.parent().parent().eq(0).hasClass('b2b-tree-checkbox')) {
                        elem.attr('tabindex', -1);                        
                        return;
                    }
                    removeRootTabIndex(elem.parent());
                };
                scope.$watch('member.child', function(newVal, oldVal){                  
                    if(newVal !== oldVal){
                        scope.showChild();
                    };
                });

                var checkedCount = 0;
                var nonCheckedCount = 0;
                var checkBoxesCount = 0;

                if(element.find('a').eq(0).find('input')){
                    if(scope.member.indeterminate){
                        element.find('a').eq(0).find('input').prop('indeterminate', true);
                        element.attr('aria-checked',"mixed");
                    }
                    element.attr('aria-checked',scope.member.isSelected);
                }

                element.find('a').eq(0).find('input').bind('change',function(){
                    scope.member.indeterminate = false;
                    downwardModalUpdate(scope.member);
                    downwardSelection(element);
                    upwardSelection(element);
                    element.attr('aria-checked',scope.member.isSelected);
                     if (scope.member.onSelect !== undefined) {
                        scope.member.onSelect(scope.member);
                    }
                });

                element.find('a').eq(0).find('input').bind('click',function(){
                    var elem = angular.element(this);
                    if(scope.member.indeterminate){
                        scope.member.indeterminate = false;
                        scope.member.isSelected = true;
                        elem.prop('indeterminate', false);
                        elem.prop('checked', true);
                        elem.triggerHandler('change');
                    }
                });

                var groupNode = false;
                var checkedTreeNode = false;

                var isCheckboxSelected = function(elem){
                    checkedTreeNode = false;
                    checkedTreeNode = angular.element(angular.element(elem).find('a').eq(0))[0].querySelector('input.treeCheckBox').checked;
                }

                var findCheckbox = function(elem){
                    return angular.element(angular.element(elem).find('a').eq(0))[0].querySelector('input.treeCheckBox');
                }

                var updateGrpNodeCheckboxes = function(elem, checked){
                    angular.element(angular.element(elem).find('a').eq(0))[0].querySelector('input.treeCheckBox').checked = checked;
                }

                
                var isGroupNode = function(elem){
                    groupNode = false;
                    if(angular.element(angular.element(elem).find('a').eq(0))[0].querySelector('input.grpTreeCheckbox')){
                        groupNode = true;
                    }
                }

                var downwardModalUpdate = function(curMember){
                    angular.forEach(curMember.child, function(childMember, key) {
                        childMember.isSelected = curMember.isSelected;
                        childMember.indeterminate = false;
                        if(angular.isArray(childMember.child) && scope.member.child.length > 0){
                            downwardModalUpdate(childMember);
                        }
                    });
                }

                var downwardSelection = function(elem){
                    if(findCheckbox(elem)){
                        isCheckboxSelected(elem)
                    } 
                    if(angular.element(elem).find('ul').length > 0){
                        var childNodes = angular.element(elem).find('ul').eq(0).children('li');
                        for(var i=0; i<childNodes.length; i++){
                            if(findCheckbox(childNodes[i])){
                                isGroupNode(childNodes[i]);
                                angular.element(findCheckbox(childNodes[i])).prop('indeterminate', false);
                                angular.element(childNodes[i]).attr('aria-checked',checkedTreeNode);
                                if(groupNode){
                                    updateGrpNodeCheckboxes(childNodes[i],checkedTreeNode);
                                }else{
                                    angular.element(childNodes[i]).scope().member.isSelected = checkedTreeNode;
                                    angular.element(childNodes[i]).scope().member.indeterminate = false
                                    angular.element(childNodes[i]).scope().$apply();
                                }
                                downwardSelection(childNodes[i]);
                            }
                        }

                    }
                }
                var upwardSelection = function(elem){
                    if(!elem.parent().parent().eq(0).hasClass('b2b-tree-checkbox')){
                        var childNodes = elem.parent().parent().find('ul').eq(0).children('li');
                        checkedCount = 0;
                        nonCheckedCount = 0;
                        checkBoxesCount = 0;    
                        for(i=0; i<childNodes.length; i++){
                            if(findCheckbox(childNodes[i])){
                                isGroupNode(childNodes[i]);
                                isCheckboxSelected(childNodes[i]);
                                checkBoxesCount++;
                                if(checkedTreeNode){
                                    checkedCount++;
                                }else if(!angular.element(angular.element(angular.element(childNodes[i]).find('a').eq(0))[0].querySelector('input.treeCheckBox')).prop('indeterminate')){
                                    nonCheckedCount++;
                                }
                            }
                        }
                        var parentNodeScope;
                        parentNodeScope = angular.element(elem.parent().parent()).scope();
                        if(findCheckbox(elem.parent().parent())){
                            if(nonCheckedCount == checkBoxesCount){
                                angular.element(findCheckbox(elem.parent().parent())).prop('indeterminate', false);
                                if(parentNodeScope &&  parentNodeScope.member){
                                    parentNodeScope.member.isSelected = false;
                                    parentNodeScope.member.indeterminate = false;
                                }else{
                                    updateGrpNodeCheckboxes(elem.parent().parent(),false);
                                }
                                angular.element(elem.parent().parent()).attr('aria-checked',false);
                            }else if(checkedCount == checkBoxesCount){
                                angular.element(findCheckbox(elem.parent().parent())).prop('indeterminate', false);
                                if(parentNodeScope &&  parentNodeScope.member){
                                    parentNodeScope.member.isSelected = true;
                                    parentNodeScope.member.indeterminate = false;
                                }else{
                                    updateGrpNodeCheckboxes(elem.parent().parent(),true);
                                }
                                angular.element(elem.parent().parent()).attr('aria-checked',true);
                            }else{
                                angular.element(findCheckbox(elem.parent().parent())).prop('indeterminate', true);
                                if(parentNodeScope &&  parentNodeScope.member){
                                    parentNodeScope.member.isSelected = false;
                                    parentNodeScope.member.indeterminate = true;
                                }else{
                                    updateGrpNodeCheckboxes(elem.parent().parent(),false);
                                }
                                angular.element(elem.parent().parent()).attr('aria-checked',"mixed");
                            }
                            if(parentNodeScope &&  parentNodeScope.member){
                                parentNodeScope.$apply();
                            }        
                        }
                        
                        
                        
                        if(elem.parent().parent().attr('role') == "treeitem"){
                            upwardSelection(elem.parent().parent());
                        }
                    }    
                }

                scope.showChild = function () {
                        if (!element.hasClass('grouped')) {
                            if (angular.isArray(scope.member.child) && scope.member.child.length > 0 && (scope.member.divide === undefined || scope.member.child.length < scope.member.divide)) {
                                scope.groupIt = false;
                                element.addClass('grouped');
                                element.append("<b2b-tree-node-checkbox collection='member.child' group-it='" + scope.groupIt + "'></b2b-tree-node-checkbox>");
                                $compile(element.contents())(scope);
                                if(scope.member.active && scope.member.active === true){
                                    angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).removeClass('icon-collapsed');
                                };
                                if(scope.member.selected && scope.member.selected === true){
                                    element.attr('tabindex', 0);
                                    removeRootTabIndex(element);
                                };
                                if(scope.member.active && scope.member.active == undefined){
                                    angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).addClass('icon-collapsed');
                                };
                            } else if (scope.member.child && scope.member.divide && scope.member.child.length > scope.member.divide) {
                                element.addClass('grouped');
                                scope.groupIt = true;
                                var j = 0;
                                var grpName = '';
                                if(scope.member.child[0].groupName !== undefined){
                                    grpName = scope.member.child[0].groupName;
                                }
                                else{
                                    var toSlice = scope.member.child[0].name.search(' ');
                                    grpName = scope.member.child[0].name.slice(0, toSlice);
                                }

                                for (i = 0; i < scope.member.child.length; i += scope.member.divide) {
                                    j = 0;
                                    for (j = j + i; j < (i + scope.member.divide); j++) {                                        
                                        if (j === scope.member.child.length) {
                                            scope.member.child[j - 1].grpChild = grpName + ' ' + (i + 1) + ' - ' + (scope.member.child.length);
                                            break;
                                            
                                            if(scope.member.child[j-1].active && scope.member.child[j-1].active===true){
                                                scope.member.child[j-1].activeGrp = true;
                                            };
                                            
                                        }
                                        if (i + scope.member.divide > scope.member.child.length) {
                                            scope.member.child[j].grpChild = grpName + ' ' + (i + 1) + ' - ' + (scope.member.child.length);
                                            if(scope.member.child[j].active && scope.member.child[j].active===true){
                                                scope.member.child[j].activeGrp = true;
                                            };

                                        } else {
                                            scope.member.child[j].grpChild = grpName + ' ' + (i + 1) + ' - ' + (i + scope.member.divide);
                                            if(scope.member.child[j].active && scope.member.child[j].active===true){
                                                scope.member.child[j].activeGrp = true;
                                            };
                                        }
                                    }
                                }
                                if(scope.member.divide){
                                    element.append("<b2b-tree-node-checkbox collection='member.child' group-it='" + scope.groupIt + "'></b2b-tree-node-checkbox>");
                                } else {
                                    element.append("<b2b-tree-node-checkbox collection='member.child' group-it='" + scope.groupIt + "'></b2b-tree-node-checkbox>");
                                }
                                $compile(element.contents())(scope);
                                if(scope.member.active && scope.member.active === true){
                                    angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).removeClass('icon-collapsed');
                                };
                                
                                if( scope.member.active && scope.member.active == undefined){
                                    angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).addClass('icon-collapsed');
                                };
                            }
                        }
                        $timeout(function () {
                            if(!scope.member.indeterminate){
                                downwardSelection(element);
                            }    
                        });  

                };
                
                if(scope.member.active && scope.member.active == true){
                    scope.showChild();
                };
                if(scope.member.active == undefined && !element.find('a').eq(0).hasClass('active') && scope.member.child !== undefined){
                    angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).addClass('icon-collapsed');
                }
                else if(scope.member.child == undefined){
                    angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).addClass('icon-circle');
                    if(scope.$parent.$index === 0) {
                        element.find('a').eq(0).append('<span class="first-link"></span>');
                    };
                };
                
                angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).bind('click', function (evt) {
                    scope.showChild();
                    var expandFunc = scope.member.onExpand;
                    if (element.find('a').eq(0).hasClass('active') && scope.member.onExpand !== undefined) {
                       var eValue = scope.member.onExpand(scope.member);
                    }
                    if (!element.find('a').eq(0).hasClass('active') && scope.member.onCollapse !== undefined) {
                        scope.member.onCollapse(scope.member);
                    }
                });

                angular.element(element[0].querySelectorAll('.treeNodeName')).eq(0).bind('click', function (evt) {

                });
                
            }
        }
}])
    .directive('b2bTreeNodeLink', ['keymap', '$timeout', function (keymap, $timeout) {
        return {
            restrict: 'A',
            link: function (scope, element, attr, ctrl) {
                var rootE, parentE, upE, downE;
                var closeOthersUp = function (elem) {
                    
                    if (elem.find('a').eq(0).hasClass('active')) {
                        activeToggle(elem);
                        return;
                    }
                    if (elem.hasClass('bg')) {
                        elem.removeClass('bg');
                    }
                    if (elem[0].previousElementSibling !== null) {
                        closeOthersUp(angular.element(elem[0].previousElementSibling));
                    }
                };
                var closeOthersDown = function (elem) {
                    
                    if (elem.find('a').eq(0).hasClass('active')) {
                        activeToggle(elem);
                        return;
                    }
                    if (elem.hasClass('bg')) {
                        elem.removeClass('bg');
                    }
                    if (elem[0].nextElementSibling !== null) {
                        closeOthersDown(angular.element(elem[0].nextElementSibling));
                    }
                };

                var removeBackgroundUp = function (elem) {
                    
                    if (elem.hasClass('b2b-tree-checkbox')) {
                        return;
                    } else {
                        elem.parent().parent().removeClass('bg');
                        removeBackgroundUp(elem.parent().parent());
                    }
                };

                var removeBackgroundDown = function (elem) {
                    
                    angular.element(elem[0].querySelector('.bg')).removeClass('bg');
                };



                var activeToggle = function (elem) {
                    var element = elem.find('a').eq(0);
                    if (element.hasClass('active')) {
                        elem.removeClass('bg');
                        if (!angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).hasClass('icon-circle')) {
                            element.removeClass('active');
                            elem.attr('aria-expanded', 'false');
                            angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).removeClass('icon-expanded');
                            angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).addClass('icon-collapsed');
                        }
                    } else {
                        elem.addClass('bg');
                        if (!angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).hasClass('icon-circle')) {
                            element.addClass('active');
                            elem.attr('aria-expanded', 'true');
                            angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).removeClass('icon-collapsed');
                            angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).addClass('icon-expanded');
                        }
                    }
                };
                angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).bind('click', function (evt) {
                    
                        if (element[0].previousElementSibling) {
                            closeOthersUp(angular.element(element[0].previousElementSibling));
                        }
                        if (element[0].nextElementSibling) {
                            closeOthersDown(angular.element(element[0].nextElementSibling));
                        }

                        activeToggle(element);

                    removeBackgroundDown(element);
                    removeBackgroundUp(element);
                    evt.stopPropagation();                    
                });
                
                if (element.parent().parent().hasClass('b2b-tree-checkbox') && (element.parent()[0].previousElementSibling === null)) {
                    element.attr('tabindex', 0);
                }
                
                var isRoot = function (elem) {
                    if (elem.parent().parent().eq(0).hasClass('b2b-tree-checkbox')) {
                        return true;
                    } else {
                        return false;
                    }
                };
                var findRoot = function (elem) {
                    if (isRoot(elem)) {
                        rootE = elem;
                        return;
                    }
                    findRoot(elem.parent());
                };

                var findPreActive = function (elem) {

                    if (!(elem.hasClass("active"))) {
                        return;
                    } else {
                        var childElems = angular.element(elem[0].nextElementSibling.children);
                        lastE = angular.element(childElems[childElems.length - 1]);
                        if (lastE.find('a').eq(0).hasClass('active')) {
                            findPreActive(lastE.find('a').eq(0));
                        }
                        upE = lastE;
                    }
                };

                var findUp = function (elem) {
                    if (isRoot(elem)) {
                        upE = elem;
                        return;
                    }
                    if (elem[0].previousElementSibling !== null && !angular.element(elem[0].previousElementSibling).hasClass('tree-hide')) {
                        upE = angular.element(elem[0].previousElementSibling);
                        if (upE.find('a').eq(0).hasClass('active')) {
                            findPreActive(upE.find('a').eq(0));
                        }
                    } else {
                        upE = elem.parent().parent();
                    }
                };

                var downElement = function (elem) {
                    if (elem.next().hasClass('tree-hide')) {
                        downElement(elem.next());
                    } else {
                        downE = elem.next();
                    }
                }
                var isBottomElem = false;
                var downParent = function(liElem){
                    if(liElem.eq(0).parent().parent().eq(0).hasClass('b2b-tree-checkbox')){
                        isBottomElem = true;
                        return;
                    }
                    if(liElem.next().length !== 0){
                        downE = liElem.next().eq(0);
                        return;
                    }
                    else {
                        downParent(liElem.parent().parent());
                    }
                }
                
                var findDown = function (elem) {
                    if (isRoot(elem.parent()) && !elem.hasClass('active')) {
                        downE = elem.parent();
                        return;
                    }
                    if (elem.hasClass('active')) {
                        downE = elem.next().find('li').eq(0);
                        if (downE.hasClass('tree-hide')) {
                            downElement(downE);
                        }

                    } else {
                        downParent(elem.parent());
                        if(isBottomElem === true){
                            downE = elem.parent();
                            isBottomElem = false;
                        }
                    }
                };
                element.bind('keydown', function (evt) {
                    switch (evt.keyCode) {
                    case keymap.KEY.HOME:
                        evt.preventDefault();
                        evt.stopPropagation();
                        element.attr('tabindex', -1);
                        findRoot(element);
                        rootE.eq(0).attr('tabindex', 0);
                        rootE[0].focus();
                        break;
                    case keymap.KEY.LEFT:
                        evt.preventDefault();
                        evt.stopPropagation();
                        if (!isRoot(element)) {
                            if(element.find('a').eq(0).hasClass('active')){
                                angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).triggerHandler('click');
                                return;
                            }
                            element.attr('tabindex', -1);
                            parentE = element.parent().parent();
                            parentE.attr('tabindex', 0);
                            parentE[0].focus();
                        } else {
                            if (element.find('a').eq(0).hasClass('active')) {
                                angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).triggerHandler('click');
                            }
                        };
                        break;
                    case keymap.KEY.UP:
                        evt.preventDefault();
                        evt.stopPropagation();
                        element.attr('tabindex', -1);
                        findUp(element);
                        upE.eq(0).attr('tabindex', 0);
                        upE[0].focus();
                        break;
                    case keymap.KEY.RIGHT:
                        evt.preventDefault();
                        evt.stopPropagation();
                        if(angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).hasClass('icon-circle')){
                            break;
                        }    
                        if (!element.find('a').eq(0).hasClass('active')) {
                            angular.element(element[0].querySelectorAll('i.expandCollapseIcon')).eq(0).triggerHandler('click');
                        }
                        else {
                            element.attr('tabindex', -1);
                            findDown(element.find('a').eq(0));
                            downE.eq(0).attr('tabindex', 0);
                            downE[0].focus();                            
                        }                        
                        break;
                    case keymap.KEY.DOWN:
                        evt.preventDefault();
                        element.attr('tabindex', -1);
                        findDown(element.find('a').eq(0));
                        downE.eq(0).attr('tabindex', 0);
                        downE[0].focus();
                        evt.stopPropagation();
                        break;
                    case keymap.KEY.SPACE:
                    case keymap.KEY.ENTER:
                        evt.preventDefault();
                        evt.stopPropagation();
                        if(angular.isDefined(element.scope().member.isSelected)){
                            element.scope().member.isSelected = !element.scope().member.isSelected;
                            element.scope().member.indeterminate = false;
                            element.scope().$apply();
                            element.find('a').eq(0).find('input').prop('indeterminate', false);
                            element.find('a').eq(0).find('input').triggerHandler('change');
                        }
                        break;    
                    default:
                        break;
                    }
                });
            }
        };
    }]);
/**
 * @ngdoc directive
 * @name Progress & usage indicators.att:usageBar
 *
 * @description
 *  <file src="src/usageBar/docs/readme.md" />
 *
 * @usage
 * <div class="progress progress-success" b2b-usage-bar="{{usageBarVal3}}" role="progressbar" aria-valuetext="40% usage"> </div>
 * 
    <div class="progress" b2b-usage-bar="{{usageBarVal2}}" is-animated='true' role="progressbar" aria-valuetext="20% usage"> </div>
        <span class="hidden-spoken">20% usage with animation</span> 

        <div class="usage-bar">
            <div class="usage-text"><i class="cssIcon-error valign-top"></i> 1/2 increments</div>
            <div class="usage-text text-right"><b>{{usageBarIncrementedVal1}}%</b> used</div>
        </div>
        <div class="progress increment progress-thin" b2b-usage-bar="{{usageBarIncrementedVal1}}" no-of-segments='2' bar-type-class='bar-success' ms-delay='5000' is-animated="true" role="progressbar" aria-valuetext="100% of the first half. 10% of the second half. 60% total used."></div>
        <div class="usage-bar">
            <div class="usage-text">Additional description text</div>
        </div>

 * @example
 *  <section id="code">   
 <b>HTML + AngularJS</b>
 <example module="b2b.att">
 <file src="src/usageBar/docs/demo.html" />
 <file src="src/usageBar/docs/demo.js" />
 </example>
 </section>
 *
 */
angular.module('b2b.att.usageBar', ['b2b.att.utilities'])
         .directive('b2bUsageBar', ['$window', '$timeout',  function($window, $timeout) {
                return {
                    restrict: 'A',
					transclude: true,
                    scope: {
                        noOfSegments: '=',
                        barTypeClass: '@?',
                        msDelay: '=?'
					 },
                    templateUrl: 'b2bTemplate/usageBar/usageBar.html',
                    link: function(scope, ele, attr) {
                       scope.changesBarValue = function(){
                        if (!(angular.isNumber(scope.noOfSegments) && scope.noOfSegments > 0)) {
                            scope.noOfSegments = 1;
                        }
                        scope.barsArray = [];
                        for (var i=0; i < scope.noOfSegments; i++) {
                            scope.barsArray.push({showItem : true});
                        }

                        if (!angular.isNumber(scope.msDelay)) {
                            scope.msDelay = 5000;
                        }
                        
                        var transition = 'width ' + scope.msDelay + 'ms';

                        var animateStyle = {
                            '-webkit-transition': transition,
                            '-moz-transition': transition,
                            '-ms-transition': transition,      
                            '-o-transition': transition,
                            transition: transition
                        };

                        if (attr.isAnimated) {
                            for (var i = 0; i < scope.barsArray.length; i++) {
                                angular.forEach(animateStyle, function(value, key) {
                                    scope.barsArray[i][key] = value;
                                });
                            }
                        }
                       
                        $timeout(function() { 
				            var percentDivEach = 100/scope.noOfSegments;
                            
                            var fullBars = Math.floor(attr.b2bUsageBar / percentDivEach);
                            var cutOffBarWidth = attr.b2bUsageBar % percentDivEach;
                            var i=0;
                            while(i < fullBars) {
                                scope.barsArray[i].width = percentDivEach + '%';
                                scope.barsArray[i].showItem = true;
                                i++;
                            }
                            if(cutOffBarWidth > 0) {
                                scope.barsArray[i].width = cutOffBarWidth + '%';
                                scope.barsArray[i].showItem = true;
                                i++;
                            }
                            while(i < scope.barsArray.length) {
                                scope.barsArray[i].showItem = false;
                                i++;
                            } 
						   });
                        
                    }
					attr.$observe('b2bUsageBar',function(newValue){
					    scope.changesBarValue();
					});
					scope.$watch('[noOfSegments,msDelay]',function(newValue){ 
						scope.changesBarValue();
					});
                    scope.changesBarValue();
				 }
                };

            }]); 

angular.module('b2b.att.collapse', ['b2b.att.transition'])

// The collapsible directive indicates a block of html that will expand and collapse
.directive('b2bCollapse', ['$transition', function($transition) {
    // CSS transitions don't work with height: auto, so we have to manually change the height to a
    // specific value and then once the animation completes, we can reset the height to auto.
    // Unfortunately if you do this while the CSS transitions are specified (i.e. in the CSS class
    // "collapse") then you trigger a change to height 0 in between.
    // The fix is to remove the "collapse" CSS class while changing the height back to auto - phew!

    var props = {
        open: {
            marginTop: null,
            marginBottom: null,
            paddingTop: null,
            paddingBottom: null,
            display: 'block'
        },
        closed: {
            marginTop: 0,
            marginBottom: 0,
            paddingTop: 0,
            paddingBottom: 0,
            display: 'none'
        }
    };

    var fixUpHeight = function(scope, element, height) {
        // We remove the collapse CSS class to prevent a transition when we change to height: auto
        element.removeClass('b2bCollapse');
        element.css({height: height});
        //adjusting for any margin or padding
        if (height === 0) {
            element.css(props.closed);
        } else {
            element.css(props.open);
        }
        // It appears that  reading offsetWidth makes the browser realise that we have changed the
        // height already :-/
        var x = element[0].offsetWidth;
        element.addClass('b2bCollapse');
    };

    return {
        link: function(scope, element, attrs) {
            var isCollapsed;
            var initialAnimSkip = true;
            scope.$watch(function() {
                return element[0].scrollHeight;
            }, function(value) {
                //The listener is called when scrollHeight changes
                //It actually does on 2 scenarios: 
                // 1. Parent is set to display none
                // 2. angular bindings inside are resolved
                //When we have a change of scrollHeight we are setting again the correct height if the group is opened
                if (element[0].scrollHeight !== 0) {
                    if (!isCollapsed) {
                        if (initialAnimSkip) {
                            fixUpHeight(scope, element, element[0].scrollHeight + 'px');
                        } else {
                            fixUpHeight(scope, element, 'auto');
                            element.css({overflow: 'visible'});
                        }
                    }
                }
            });

            scope.$watch(attrs.b2bCollapse, function(value) {
                if (value) {
                    collapse();
                } else {
                    expand();
                }
            });


            var currentTransition;
            var doTransition = function(change) {
                if (currentTransition) {
                    currentTransition.cancel();
                }
                currentTransition = $transition(element, change);
                currentTransition.then(
                        function() {
                            currentTransition = undefined;
                        },
                        function() {
                            currentTransition = undefined;
                        }
                );
                return currentTransition;
            };

            var expand = function() {
                scope.postTransition = true; 
                if (initialAnimSkip) {
                    initialAnimSkip = false;
                    if (!isCollapsed) {
                        fixUpHeight(scope, element, 'auto');
                    }
                } else {
                    //doTransition({ height : element[0].scrollHeight + 'px' })
                    doTransition(angular.extend({height: element[0].scrollHeight + 'px'}, props.open))
                            .then(function() {
                                // This check ensures that we don't accidentally update the height if the user has closed
                                // the group while the animation was still running
                                if (!isCollapsed) {
                                    fixUpHeight(scope, element, 'auto');
                                }
                            });
                }
                isCollapsed = false;
            };

            var collapse = function() {
                isCollapsed = true;
                if (initialAnimSkip) {
                    initialAnimSkip = false;
                    fixUpHeight(scope, element, 0);
                } else {
                    fixUpHeight(scope, element, element[0].scrollHeight + 'px');
                    doTransition(angular.extend({height: 0}, props.closed)).then(function() {
                        scope.postTransition = false;
                    });
                    element.css({overflow: 'hidden'});
                }
            };
        }
    };
}]);
angular.module('b2b.att.position', [])

.factory('$position', ['$document', '$window', function ($document, $window) {
    function getStyle(el, cssprop) {
        if (el.currentStyle) { //IE
            return el.currentStyle[cssprop];
        } else if ($window.getComputedStyle) {
            return $window.getComputedStyle(el)[cssprop];
        }
        // finally try and get inline style
        return el.style[cssprop];
    }

    /**
     * Checks if a given element is statically positioned
     * @param element - raw DOM element
     */
    function isStaticPositioned(element) {
        return (getStyle(element, "position") || 'static') === 'static';
    }

    /**
     * returns the closest, non-statically positioned parentOffset of a given element
     * @param element
     */
    var parentOffsetEl = function (element) {
        var docDomEl = $document[0];
        var offsetParent = element.offsetParent || docDomEl;
        while (offsetParent && offsetParent !== docDomEl && isStaticPositioned(offsetParent)) {
            offsetParent = offsetParent.offsetParent;
        }
        return offsetParent || docDomEl;
    };

    return {
        /**
         * Provides read-only equivalent of jQuery's position function:
         * http://api.jquery.com/position/
         */
        position: function (element) {
            var elBCR = this.offset(element);
            var offsetParentBCR = {
                top: 0,
                left: 0
            };
            var offsetParentEl = parentOffsetEl(element[0]);
            if (offsetParentEl !== $document[0]) {
                offsetParentBCR = this.offset(angular.element(offsetParentEl));
                offsetParentBCR.top += offsetParentEl.clientTop - offsetParentEl.scrollTop;
                offsetParentBCR.left += offsetParentEl.clientLeft - offsetParentEl.scrollLeft;
            }

            return {
                width: element.prop('offsetWidth'),
                height: element.prop('offsetHeight'),
                top: elBCR.top - offsetParentBCR.top,
                left: elBCR.left - offsetParentBCR.left
            };
        },

        /**
         * Provides read-only equivalent of jQuery's offset function:
         * http://api.jquery.com/offset/
         */
        offset: function (element) {
            var boundingClientRect = element[0].getBoundingClientRect();
            return {
                width: element.prop('offsetWidth'),
                height: element.prop('offsetHeight'),
                top: boundingClientRect.top + ($window.pageYOffset || $document[0].body.scrollTop || $document[0].documentElement.scrollTop),
                left: boundingClientRect.left + ($window.pageXOffset || $document[0].body.scrollLeft || $document[0].documentElement.scrollLeft)
            };
        },
		
		 /**
         * Provides functionality to check whether an element is in view port.
         */
        isElementInViewport: function (element) {
            if (element) {
                var rect = element[0].getBoundingClientRect();
                return (
                    rect.top >= 0 &&
                    rect.left >= 0 &&
                    rect.bottom <= ($window.innerHeight || $document[0].documentElement.clientHeight) &&
                    rect.right <= ($window.innerWidth || $document[0].documentElement.clientWidth)
                );
            } else {
                return false;
            }
        }
    };
}])

.factory('$isElement', [function () {
    var isElement = function (currentElem, targetElem, alternateElem) {
        if (currentElem[0] === targetElem[0]) {
            return true;
        } else if (currentElem[0] === alternateElem[0]) {
            return false;
        } else {
            return isElement((currentElem.parent()[0] && currentElem.parent()) || targetElem, targetElem, alternateElem);
        }
    };

    return isElement;
}])

.directive('attPosition', ['$position', function ($position) {
    return {
        restrict: 'A',
        link: function (scope, elem, attr) {
            scope.$watchCollection(function () {
                return $position.position(elem);
            }, function (value) {
                scope[attr.attPosition] = value;
            });
        }
    };
}]);

var duScrollDefaultEasing = function (x) {
  'use strict';

  if(x < 0.5) {
    return Math.pow(x*2, 2)/2;
  }
  return 1-Math.pow((1-x)*2, 2)/2;
};

var duScroll = angular.module('ds2Scroll', [  
  'duScroll.smoothScroll',
  'duScroll.scrollContainer',  
  'duScroll.scrollHelpers'
])
  //Default animation duration for smoothScroll directive
  .value('duScrollDuration', 350)
  //Wether or not multiple scrollspies can be active at once
  .value('duScrollGreedy', false)
  //Default offset for smoothScroll directive
  .value('duScrollOffset', 0)
  //Default easing function for scroll animation
  .value('duScrollEasing', duScrollDefaultEasing)
  //Which events on the container (such as body) should cancel scroll animations
  .value('duScrollCancelOnEvents', 'scroll mousedown mousewheel touchmove keydown')
  //Whether or not to activate the last scrollspy, when page/container bottom is reached
  .value('duScrollBottomSpy', false)
  //Active class name
  .value('duScrollActiveClass', 'active');

if (typeof module !== 'undefined' && module && module.exports) {
  module.exports = duScroll;
}
angular.module('duScroll.scrollHelpers', ['duScroll.requestAnimation'])
.run(["$window", "$q", "cancelAnimation", "requestAnimation", "duScrollEasing", "duScrollDuration", "duScrollOffset", "duScrollCancelOnEvents", function($window, $q, cancelAnimation, requestAnimation, duScrollEasing, duScrollDuration, duScrollOffset, duScrollCancelOnEvents) {
  'use strict';

  var proto = {};

  var isDocument = function(el) {
    return (typeof HTMLDocument !== 'undefined' && el instanceof HTMLDocument) || (el.nodeType && el.nodeType === el.DOCUMENT_NODE);
  };

  var isElement = function(el) {
    return (typeof HTMLElement !== 'undefined' && el instanceof HTMLElement) || (el.nodeType && el.nodeType === el.ELEMENT_NODE);
  };

  var unwrap = function(el) {
    return isElement(el) || isDocument(el) ? el : el[0];
  };

  proto.duScrollTo = function(left, top, duration, easing) {
    var aliasFn;
    if(angular.isElement(left)) {
      aliasFn = this.duScrollToElement;
    } else if(angular.isDefined(duration)) {
      aliasFn = this.duScrollToAnimated;
    }
    if(aliasFn) {
      return aliasFn.apply(this, arguments);
    }
    var el = unwrap(this);
    if(isDocument(el)) {
      return $window.scrollTo(left, top);
    }
    el.scrollLeft = left;
    el.scrollTop = top;
  };

  var scrollAnimation, deferred;
  proto.duScrollToAnimated = function(left, top, duration, easing) {
    if(duration && !easing) {
      easing = duScrollEasing;
    }
    var startLeft = this.duScrollLeft(),
        startTop = this.duScrollTop(),
        deltaLeft = Math.round(left - startLeft),
        deltaTop = Math.round(top - startTop);

    var startTime = null, progress = 0;
    var el = this;

    var cancelScrollAnimation = function($event) {
      if (!$event || (progress && $event.which > 0)) {
        if(duScrollCancelOnEvents) {
          el.unbind(duScrollCancelOnEvents, cancelScrollAnimation);
        }
        cancelAnimation(scrollAnimation);
        deferred.reject();
        scrollAnimation = null;
      }
    };

    if(scrollAnimation) {
      cancelScrollAnimation();
    }
    deferred = $q.defer();

    if(duration === 0 || (!deltaLeft && !deltaTop)) {
      if(duration === 0) {
        el.duScrollTo(left, top);
      }
      deferred.resolve();
      return deferred.promise;
    }

    var animationStep = function(timestamp) {
      if (startTime === null) {
        startTime = timestamp;
      }

      progress = timestamp - startTime;
      var percent = (progress >= duration ? 1 : easing(progress/duration));

      el.scrollTo(
        startLeft + Math.ceil(deltaLeft * percent),
        startTop + Math.ceil(deltaTop * percent)
      );
      if(percent < 1) {
        scrollAnimation = requestAnimation(animationStep);
      } else {
        if(duScrollCancelOnEvents) {
          el.unbind(duScrollCancelOnEvents, cancelScrollAnimation);
        }
        scrollAnimation = null;
        deferred.resolve();
      }
    };

    //Fix random mobile safari bug when scrolling to top by hitting status bar
    el.duScrollTo(startLeft, startTop);

    if(duScrollCancelOnEvents) {
      el.bind(duScrollCancelOnEvents, cancelScrollAnimation);
    }

    scrollAnimation = requestAnimation(animationStep);
    return deferred.promise;
  };

  proto.duScrollToElement = function(target, offset, duration, easing) {
    var el = unwrap(this);
    if(!angular.isNumber(offset) || isNaN(offset)) {
      offset = duScrollOffset;
    }
    var top = this.duScrollTop() + unwrap(target).getBoundingClientRect().top - offset;
    if(isElement(el)) {
      top -= el.getBoundingClientRect().top;
    }
    return this.duScrollTo(0, top, duration, easing);
  };

  proto.duScrollLeft = function(value, duration, easing) {
    if(angular.isNumber(value)) {
      return this.duScrollTo(value, this.duScrollTop(), duration, easing);
    }
    var el = unwrap(this);
    if(isDocument(el)) {
      return $window.scrollX || document.documentElement.scrollLeft || document.body.scrollLeft;
    }
    return el.scrollLeft;
  };
  proto.duScrollTop = function(value, duration, easing) {
    if(angular.isNumber(value)) {
      return this.duScrollTo(this.duScrollLeft(), value, duration, easing);
    }
    var el = unwrap(this);
    if(isDocument(el)) {
      return $window.scrollY || document.documentElement.scrollTop || document.body.scrollTop;
    }
    return el.scrollTop;
  };

  proto.duScrollToElementAnimated = function(target, offset, duration, easing) {
    return this.duScrollToElement(target, offset, duration || duScrollDuration, easing);
  };

  proto.duScrollTopAnimated = function(top, duration, easing) {
    return this.duScrollTop(top, duration || duScrollDuration, easing);
  };

  proto.duScrollLeftAnimated = function(left, duration, easing) {
    return this.duScrollLeft(left, duration || duScrollDuration, easing);
  };

  angular.forEach(proto, function(fn, key) {
    angular.element.prototype[key] = fn;

    //Remove prefix if not already claimed by jQuery / ui.utils
    var unprefixed = key.replace(/^duScroll/, 'scroll');
    if(angular.isUndefined(angular.element.prototype[unprefixed])) {
      angular.element.prototype[unprefixed] = fn;
    }
  });

}]);

angular.module('duScroll.polyfill', [])
.factory('polyfill', ["$window", function($window) {
  'use strict';

  var vendors = ['webkit', 'moz', 'o', 'ms'];

  return function(fnName, fallback) {
    if($window[fnName]) {
      return $window[fnName];
    }
    var suffix = fnName.substr(0, 1).toUpperCase() + fnName.substr(1), vLen = vendors.length;
    for(var key, i = 0; i < vLen; i++) {
      key = vendors[i]+suffix;
      if($window[key]) {
        return $window[key];
      }
    }
    return fallback;
  };
}]);

angular.module('duScroll.requestAnimation', ['duScroll.polyfill'])
.factory('requestAnimation', ["polyfill", "$timeout", function(polyfill, $timeout) {
  'use strict';

  var lastTime = 0;
  var fallback = function(callback, element) {
    var currTime = new Date().getTime();
    var timeToCall = Math.max(0, 16 - (currTime - lastTime));
    var id = $timeout(function() { callback(currTime + timeToCall); },
      timeToCall);
    lastTime = currTime + timeToCall;
    return id;
  };

  return polyfill('requestAnimationFrame', fallback);
}])
.factory('cancelAnimation', ["polyfill", "$timeout", function(polyfill, $timeout) {
  'use strict';

  var fallback = function(promise) {
    $timeout.cancel(promise);
  };

  return polyfill('cancelAnimationFrame', fallback);
}]);

angular.module('duScroll.scrollContainerAPI', [])
.factory('scrollContainerAPI', ["$document", function($document) {
  'use strict';

  var containers = {};

  var setContainer = function(scope, element) {
    var id = scope.$id;
    containers[id] = element;
    return id;
  };

  var getContainerId = function(scope) {
    if(containers[scope.$id]) {
      return scope.$id;
    }
    if(scope.$parent) {
      return getContainerId(scope.$parent);
    }
    return;
  };

  var getContainer = function(scope) {
    var id = getContainerId(scope);
    return id ? containers[id] : $document;
  };

  var removeContainer = function(scope) {
    var id = getContainerId(scope);
    if(id) {
      delete containers[id];
    }
  };

  return {
    getContainerId:   getContainerId,
    getContainer:     getContainer,
    setContainer:     setContainer,
    removeContainer:  removeContainer
  };
}]);

angular.module('duScroll.smoothScroll', ['duScroll.scrollHelpers', 'duScroll.scrollContainerAPI'])
.directive('duSmoothScroll', ["duScrollDuration", "duScrollOffset", "scrollContainerAPI", function(duScrollDuration, duScrollOffset, scrollContainerAPI) {
  'use strict';

  return {
    link : function($scope, $element, $attr) {
      $element.on('click', function(e) {
        if((!$attr.href || $attr.href.indexOf('#') === -1) && $attr.duSmoothScroll === '') return;

        var id = $attr.href ? $attr.href.replace(/.*(?=#[^\s]+$)/, '').substring(1) : $attr.duSmoothScroll;

        var target = document.getElementById(id) || document.getElementsByName(id)[0];
        if(!target || !target.getBoundingClientRect) return;

        if (e.stopPropagation) e.stopPropagation();
        if (e.preventDefault) e.preventDefault();

        var offset    = $attr.offset ? parseInt($attr.offset, 10) : duScrollOffset;
        var duration  = $attr.duration ? parseInt($attr.duration, 10) : duScrollDuration;
        var container = scrollContainerAPI.getContainer($scope);

        container.duScrollToElement(
          angular.element(target),
          isNaN(offset) ? 0 : offset,
          isNaN(duration) ? 0 : duration
        );
      });
    }
  };
}]);


angular.module('duScroll.scrollContainer', ['duScroll.scrollContainerAPI'])
.directive('duScrollContainer', ["scrollContainerAPI", function(scrollContainerAPI){
  'use strict';

  return {
    restrict: 'A',
    scope: true,
    compile: function compile(tElement, tAttrs, transclude) {
      return {
        pre: function preLink($scope, iElement, iAttrs, controller) {
          iAttrs.$observe('duScrollContainer', function(element) {
            if(angular.isString(element)) {
              element = document.getElementById(element);
            }

            element = (angular.isElement(element) ? angular.element(element) : iElement);
            scrollContainerAPI.setContainer($scope, element);
            $scope.$on('$destroy', function() {
              scrollContainerAPI.removeContainer($scope);
            });
          });
        }
      };
    }
  };
}]);


angular.module('b2b.att.transition', [])

.factory('$transition', ['$q', '$timeout', '$rootScope', function($q, $timeout, $rootScope) {

  var $transition = function(element, trigger, options) {
    options = options || {};
    var deferred = $q.defer();
    var endEventName = $transition[options.animation ? "animationEndEventName" : "transitionEndEventName"];

    var transitionEndHandler = function() {
      $rootScope.$apply(function() {
        element.unbind(endEventName, transitionEndHandler);
        deferred.resolve(element);
      });
    };

    if (endEventName) {
      element.bind(endEventName, transitionEndHandler);
    }

    // Wrap in a timeout to allow the browser time to update the DOM before the transition is to occur
    $timeout(function() {
      if ( angular.isString(trigger) ) {
        element.addClass(trigger);
      } else if ( angular.isFunction(trigger) ) {
        trigger(element);
      } else if ( angular.isObject(trigger) ) {
        element.css(trigger);
      }
      //If browser does not support transitions, instantly resolve
      if ( !endEventName ) {
        deferred.resolve(element);
      }
    }, 100);

    // Add our custom cancel function to the promise that is returned
    // We can call this if we are about to run a new transition, which we know will prevent this transition from ending,
    // i.e. it will therefore never raise a transitionEnd event for that transition
    deferred.promise.cancel = function() {
      if ( endEventName ) {
        element.unbind(endEventName, transitionEndHandler);
      }
      deferred.reject('Transition cancelled');
    };

    return deferred.promise;
  };

  // Work out the name of the transitionEnd event
  var transElement = document.createElement('trans');
  var transitionEndEventNames = {
    'WebkitTransition': 'webkitTransitionEnd',
    'MozTransition': 'transitionend',
    'OTransition': 'oTransitionEnd',
    'transition': 'transitionend'
  };
  var animationEndEventNames = {
    'WebkitTransition': 'webkitAnimationEnd',
    'MozTransition': 'animationend',
    'OTransition': 'oAnimationEnd',
    'transition': 'animationend'
  };
  function findEndEventName(endEventNames) {
    for (var name in endEventNames){
      if (transElement.style[name] !== undefined) {
        return endEventNames[name];
      }
    }
  }
  $transition.transitionEndEventName = findEndEventName(transitionEndEventNames);
  $transition.animationEndEventName = findEndEventName(animationEndEventNames);
  return $transition;
}])

.factory('$scrollTo', ['$window', function($window) {
    var $scrollTo = function(offsetLeft, offsetTop, duration) {
        TweenMax.to($window, duration || 1, {scrollTo: {y: offsetTop, x: offsetLeft}, ease: Power4.easeOut});
    };
    return $scrollTo;
}])
.factory('animation', function(){
    return TweenMax;
})
.factory('$progressBar', function(){

   //Provides a function to pass in code for closure purposes
   var loadingAnimationCreator = function(onUpdateCallback){

      //Use closure to setup some resuable code
      var loadingAnimation = function(callback, duration){
          TweenMax.to({}, duration, {
              onUpdateParams: ["{self}"],
              onUpdate: onUpdateCallback,
              onComplete: callback
          });
      };
      //Returns a function that takes a callback function and a duration for the animation
      return (function(){
        return loadingAnimation;
      })();
    };

  return loadingAnimationCreator;
})
.factory('$height', function(){
  var heightAnimation = function(element,duration,height,alpha){
    TweenMax.to(element,
      duration,
      {height:height, autoAlpha:alpha},
      0);
  };
  return heightAnimation;
});
angular.module('b2b.att.utilities', ['ngSanitize'])
.constant('b2bUtilitiesConfig', {
    prev: '37',
    up: '38',
    next: '39',
    down: '40',
    type: 'list',
    columns: 1,
    enableSearch: false,
    searchTimer: 200,
    circularTraversal: false
})
.constant('b2bWhenScrollEndsConstants', {
    'threshold': 100,
    'width': 0,
    'height': 0
})
// All breakpoints ranges from >= min and < max
.constant('b2bAwdBreakpoints', {
    breakpoints: {
        mobile: {
            min: 1,
            max: 768
        },
        tablet: {
            min: 768,
            max: 1025
        },
        desktop: {
            min: 1025,
            max: 1920
        }
    }
})
.filter('groupBy', function ($timeout) {
    //Custom GroupBy Filter for treeNav, returns key string and value.childarray as set of grouped elements
    return function (data, key) {
        if (!key) return data;
        var outputPropertyName = '__groupBy__' + key;
        if (!data[outputPropertyName]) {
            var result = {};
            for (var i = 0; i < data.length; i++) {
                if (!result[data[i][key]])
                    result[data[i][key]] = {};
                if (!result[data[i][key]].childArray) {
                    result[data[i][key]].childArray = [];
                }
                result[data[i][key]].childArray.push(data[i]);
                if (data[i].activeGrp && data[i].activeGrp == true) {
                    result[data[i][key]].showGroup = true;
                }
            }
            Object.defineProperty(result, 'length', {enumerable: false,value: Object.keys(result).length});
            Object.defineProperty(data, outputPropertyName, {enumerable: false,configurable: true,writable:false,value:result});
            $timeout(function(){delete data[outputPropertyName];},0,false);
        }
        return data[outputPropertyName];
    };
})
.filter('searchObjectPropertiesFilter', [function() {
    return function(items, searchText, attrs) {
        if(!searchText){
            return items;
        }
        var filtered = [];
        searchText = searchText.toLowerCase();
        angular.forEach(items, function(item) {
            angular.forEach(attrs, function(attr) {
                if (item.hasOwnProperty(attr) && (item[attr].toLowerCase().indexOf(searchText) != -1)) {
                    filtered.push(item);
                    return;
                }
            });
        });
        return filtered;
    };
}])
.filter('unsafe',[ '$sce', function ($sce) {
    return function(val){
       return $sce.trustAsHtml(val);
    };
}])
.filter('b2bHighlight', function () {
    function escapeRegexp(queryToEscape) {
        return queryToEscape.replace(/([.?*+^$[\]\\(){}|-])/g, '\\$1');
    }
    return function (matchItem, query, className) {
        return query && matchItem ? matchItem.replace(new RegExp(escapeRegexp(query), 'gi'), '<span class=\"' + className + '\">$&</span>') : matchItem;
    }
})


.filter('b2bLimitTo', function() {
    return function(actualArray, _limit, _begin) {
        var finalArray = [];
        var limit = _limit;
        var begin = _begin;
        if(isNaN(begin)) {
            begin = 0;
        }
        if(actualArray && !isNaN(limit)) {
            finalArray = actualArray.slice((begin, Math.min(begin+limit, actualArray.length)));
        } else {
            finalArray = actualArray;
        }
        return finalArray;
    };
})

.factory('b2bViewport', function() {
  /* Source: https://gist.github.com/bjankord/2399828 */
  var _viewportWidth = function() {
    var vpw;
    var webkit = (!(window.webkitConvertPointFromNodeToPage == null));

    // Webkit:
    if ( webkit ) {
      var vpwtest = document.createElement( "div" );
      // Sets test div to width 100%, !important overrides any other misc. box model styles that may be set in the CSS
      vpwtest.style.cssText = "width:100% !important; margin:0 !important; padding:0 !important; border:none !important;";
      document.documentElement.insertBefore( vpwtest, document.documentElement.firstChild );
      vpw = vpwtest.offsetWidth;
      document.documentElement.removeChild( vpwtest );
    }
    // IE 6-8:
    else if ( window.innerWidth === undefined ) {
      vpw = document.documentElement.clientWidth;
    }
    // Other:
    else{
      vpw =  window.innerWidth;
    }

    return (vpw);
  }
  return {
    viewportWidth: _viewportWidth
  };
})
.directive('b2bWhenScrollEnds', function(b2bWhenScrollEndsConstants, $log) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            /**
            * Exposed Attributes:
            *       threshold - integer - number of pixels before scrollbar hits end that callback is called
            *       width - integer - override for element's width (px)
            *       height - integer - override for element's height (px)
            *       axis - string - x/y for scroll bar axis
            */
            var threshold = parseInt(attrs.threshold, 10) || b2bWhenScrollEndsConstants.threshold;

            if (!attrs.axis || attrs.axis === '') {
                $log.warn('axis attribute must be defined for b2bWhenScrollEnds.');
                return;
            }

            if (attrs.axis === 'x') {
                visibleWidth = parseInt(attrs.width, 10) || b2bWhenScrollEndsConstants.width;
                if (element.css('width')) {
                    visibleWidth = element.css('width').split('px')[0];
                }

                element[0].addEventListener('scroll', function() {
                    var scrollableWidth = element.prop('scrollWidth');
                    if (scrollableWidth === undefined) {
                        scrollableWidth = 1;
                    }
                    var hiddenContentWidth = scrollableWidth - visibleWidth;

                    if (hiddenContentWidth - element[0].scrollLeft <= threshold) {
                        /* Scroll almost at bottom, load more rows */
                        scope.$apply(attrs.b2bWhenScrollEnds);
                    }
                });
            } else if (attrs.axis === 'y') {
                visibleHeight = parseInt(attrs.height, 10) || b2bWhenScrollEndsConstants.height;
                if (element.css('width')) {
                    visibleHeight = element.css('height').split('px')[0];
                }

                element[0].addEventListener('scroll', function() {
                    var scrollableHeight = element.prop('scrollHeight');
                    if (scrollableHeight === undefined) {
                        scrollableHeight = 1;
                    }
                    var hiddenContentHeight = scrollableHeight - visibleHeight;

                    if (hiddenContentHeight - element[0].scrollTop <= threshold) {
                        /* Scroll almost at bottom, load more rows */
                        scope.$apply(attrs.b2bWhenScrollEnds);
                    }
                });
            }
        }
    };
})

.factory('$windowBind', ['$window', '$timeout', function($window, $timeout) {
    var win = angular.element($window);
    var _scroll = function (flag, callbackFunc, scope) {
        scope.$watch(flag, function (val) {
            $timeout(function () {
                if (val) {
                    win.bind('scroll', callbackFunc);
                } else {
                    win.unbind('scroll', callbackFunc);
                }
            });
        });
    };

    var throttle = function(type, name, obj) {
        obj = obj || window;
        var running = false;
        var func = function() {
            if (running) { return; }
            running = true;
             requestAnimationFrame(function() {
                obj.dispatchEvent(new CustomEvent(name));
                running = false;
            });
        };
        obj.addEventListener(type, func);
    };

    var _resize = function(callbackFunc, scope) {
        throttle("resize", "optimizedResize");
        window.addEventListener("optimizedResize", function(event) {
            callbackFunc();
            //win.bind(event, callbackFunc);
            if (!scope.$$phase) {
                scope.$digest();
            }
        });
    };

    var _click = function (flag, callbackFunc, scope) {
        scope.$watch(flag, function (val) {
            $timeout(function () {
                if (val) {
                    win.bind('click', callbackFunc);
                } else {
                    win.unbind('click', callbackFunc);
                }
            });
        });
    };

    var _event = function (event, flag, callbackFunc, scope, timeoutFlag, timeoutValue) {
        if (timeoutFlag) {
            if (!(timeoutValue)) {
                timeoutValue = 0;
            }
            scope.$watch(flag, function (newVal, oldVal) {
                if (newVal !== oldVal) {
                    $timeout(function () {
                        if (newVal) {
                            win.bind(event, callbackFunc);
                        } else {
                            win.unbind(event, callbackFunc);
                        }
                    }, timeoutValue);
                }
            });
        } else {
            scope.$watch(flag, function (newVal, oldVal) {
                if (newVal !== oldVal) {
                    if (newVal) {
                        win.bind(event, callbackFunc);
                    } else {
                        win.unbind(event, callbackFunc);
                    }
                }
            });
        }
    };

    return {
        click: _click,
        scroll: _scroll,
        event: _event,
        resize: _resize
    };
}])

.factory('keymap', function () {
    return {
        KEY: {
            TAB: 9,
            ENTER: 13,
            ESC: 27,
            SPACE: 32,
            LEFT: 37,
            UP: 38,
            RIGHT: 39,
            DOWN: 40,
            SHIFT: 16,
            CTRL: 17,
            ALT: 18,
            PAGE_UP: 33,
            PAGE_DOWN: 34,
            HOME: 36,
            END: 35,
            BACKSPACE: 8,
            DELETE: 46,
            COMMAND: 91
        },
        MAP: { 91 : "COMMAND", 8 : "BACKSPACE" , 9 : "TAB" , 13 : "ENTER" , 16 : "SHIFT" , 17 : "CTRL" , 18 : "ALT" , 19 : "PAUSEBREAK" , 20 : "CAPSLOCK" , 27 : "ESC" , 32 : "SPACE" , 33 : "PAGE_UP", 34 : "PAGE_DOWN" , 35 : "END" , 36 : "HOME" , 37 : "LEFT" , 38 : "UP" , 39 : "RIGHT" , 40 : "DOWN" , 43 : "+" , 44 : "PRINTSCREEN" , 45 : "INSERT" , 46 : "DELETE", 48 : "0" , 49 : "1" , 50 : "2" , 51 : "3" , 52 : "4" , 53 : "5" , 54 : "6" , 55 : "7" , 56 : "8" , 57 : "9" , 59 : ";", 61 : "=" , 65 : "A" , 66 : "B" , 67 : "C" , 68 : "D" , 69 : "E" , 70 : "F" , 71 : "G" , 72 : "H" , 73 : "I" , 74 : "J" , 75 : "K" , 76 : "L", 77 : "M" , 78 : "N" , 79 : "O" , 80 : "P" , 81 : "Q" , 82 : "R" , 83 : "S" , 84 : "T" , 85 : "U" , 86 : "V" , 87 : "W" , 88 : "X" , 89 : "Y" , 90 : "Z", 96 : "0" , 97 : "1" , 98 : "2" , 99 : "3" , 100 : "4" , 101 : "5" , 102 : "6" , 103 : "7" , 104 : "8" , 105 : "9", 106 : "*" , 107 : "+" , 109 : "-" , 110 : "." , 111 : "/", 112 : "F1" , 113 : "F2" , 114 : "F3" , 115 : "F4" , 116 : "F5" , 117 : "F6" , 118 : "F7" , 119 : "F8" , 120 : "F9" , 121 : "F10" , 122 : "F11" , 123 : "F12", 144 : "NUMLOCK" , 145 : "SCROLLLOCK" , 186 : ";" , 187 : "=" , 188 : "," , 189 : "-" , 190 : "." , 191 : "/" , 192 : "`" , 219 : "[" , 220 : "\\" , 221 : "]" , 222 : "'"
        },
        isControl: function (e) {
            var k = e.keyCode;
            switch (k) {
            case this.KEY.COMMAND:
            case this.KEY.SHIFT:
            case this.KEY.CTRL:
            case this.KEY.ALT:
                return true;
            default:;
            }

            if (e.metaKey) {
                return true;
            }

            return false;
        },
        isFunctionKey: function (k) {
            k = k.keyCode ? k.keyCode : k;
            return k >= 112 && k <= 123;
        },
        isVerticalMovement: function (k) {
            return ~[this.KEY.UP, this.KEY.DOWN].indexOf(k);
        },
        isHorizontalMovement: function (k) {
            return ~[this.KEY.LEFT, this.KEY.RIGHT, this.KEY.BACKSPACE, this.KEY.DELETE].indexOf(k);
        },
        isAllowedKey: function (k) {
            return (~[this.KEY.SPACE, this.KEY.ESC, this.KEY.ENTER].indexOf(k)) || this.isHorizontalMovement(k) || this.isVerticalMovement(k);
        },
        isNumericKey: function (e) {
            var k = e.keyCode;
            if ((k >= 48 && k <= 57) || (k >= 96 && k <= 105)) {
                return true;
            } else {
                return false;
            }
        },
        isAlphaNumericKey: function (e) {
            var k = e.keyCode;
            if ((k >= 48 && k <= 57) || (k >= 96 && k <= 105) || (k >= 65 && k <= 90)) {
                return true;
            } else {
                return false;
            }
        }
    };
})

.factory('$isElement', [function () {
    var isElement = function (currentElem, targetElem, alternateElem) {
        if (currentElem[0] === targetElem[0]) {
            return true;
        } else if (currentElem[0] === alternateElem[0]) {
            return false;
        } else {
            return isElement((currentElem.parent()[0] && currentElem.parent()) || targetElem, targetElem, alternateElem);
        }
    };

    return isElement;
}])

.factory('events', function () {
    var _stopPropagation = function (evt) {
        if (evt.stopPropagation) {
            evt.stopPropagation();
        } else {
            evt.returnValue = false;
        }
    };
    var _preventDefault = function (evt) {
        if (evt.preventDefault) {
            evt.preventDefault();
        } else {
            evt.returnValue = false;
        }
    }
    return {
        stopPropagation: _stopPropagation,
        preventDefault: _preventDefault
    };
})


.factory('isHighContrast', function () {
    var _isHighContrast = function (idval)


     {
        var objDiv, objImage, strColor, strWidth, strReady;
        var strImageID = idval; // ID of image on the page

        // Create a test div
        objDiv = document.createElement('div');

        //Set its color style to something unusual
        objDiv.style.color = 'rgb(31, 41, 59)';

        // Attach to body so we can inspect it
        document.body.appendChild(objDiv);

        // Read computed color value
        strColor = document.defaultView ? document.defaultView.getComputedStyle(objDiv, null).color : objDiv.currentStyle.color;
        strColor = strColor.replace(/ /g, '');

        // Delete the test DIV
        document.body.removeChild(objDiv);

        // Check if we get the color back that we set. If not, we're in
        // high contrast mode.
        if (strColor !== 'rgb(31,41,59)') {
            return true;
        } else {
            return false;
        }
    };

    return _isHighContrast;
})

.run(['isHighContrast', '$document', function (isHighContrast, $document) {
    var html = $document.find('html').eq(0);
    if (isHighContrast()) {
        html.addClass('ds2-no-colors');
    } else {
        html.removeClass('ds2-no-colors');
    }
}])

.factory('$documentBind', ['$document', '$timeout', function ($document, $timeout) {
    var _click = function (flag, callbackFunc, scope) {
        scope.$watch(flag, function (val) {
            $timeout(function () {
                if (val) {
                    $document.bind('click', callbackFunc);
                } else {
                    $document.unbind('click', callbackFunc);
                }
            });
        });
    };

    var _touch = function (flag, callbackFunc, scope) {
        scope.$watch(flag, function (val) {
            $timeout(function () {
                if (val) {
                    $document.bind('touchstart', callbackFunc);
                } else {
                    $document.unbind('touchstart', callbackFunc);
                }
            });
        });
    };

    var _scroll = function (flag, callbackFunc, scope) {
        scope.$watch(flag, function (val) {
            $timeout(function () {
                if (val) {
                    $document.bind('scroll', callbackFunc);
                } else {
                    $document.unbind('scroll', callbackFunc);
                }
            });
        });
    };

    var _event = function (event, flag, callbackFunc, scope, timeoutFlag, timeoutValue) {
        if (timeoutFlag) {
            if (!(timeoutValue)) {
                timeoutValue = 0;
            }
            scope.$watch(flag, function (newVal, oldVal) {
                if (newVal !== oldVal) {
                    $timeout(function () {
                        if (newVal) {
                            $document.bind(event, callbackFunc);
                        } else {
                            $document.unbind(event, callbackFunc);
                        }
                    }, timeoutValue);
                }
            });
        } else {
            scope.$watch(flag, function (newVal, oldVal) {
                if (newVal !== oldVal) {
                    if (newVal) {
                        $document.bind(event, callbackFunc);
                    } else {
                        $document.unbind(event, callbackFunc);
                    }
                }
            });
        }
    };

    return {
        click: _click,
        touch: _touch,
        scroll: _scroll,
        event: _event
    };
}])

.directive('b2bOnlyNums', function (keymap) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
            var maxChars = attrs.b2bOnlyNums ? attrs.b2bOnlyNums : 4;
            elm.on('keydown', function (event) {
                if ((event.which >= 48 && event.which <= 57) || (event.which >= 96 && event.which <= 105)) {
                    // check for maximum characters allowed
                    if (elm.val().length < maxChars){
                        return true;
                    } else {
                        event.preventDefault();
                        return false;
                    }
                } else if ([8, 9, 13, 27, 37, 38, 39, 40].indexOf(event.which) > -1) {
                    // to allow backspace, tab, enter, escape, arrows
                    return true;
                } else if (event.altKey || event.ctrlKey) {
                    // to allow alter, control, and shift keys
                    return true;
                } else {
                    // to stop others
                    event.preventDefault();
                    return false;
                }
            });

            var validateString = function (value) {
                if (angular.isUndefined(value) || value === null || value === '') {
                    return ctrl.$modelValue;
                }
                return value;
            };
            ctrl.$parsers.unshift(validateString);
        }
    }
})

.directive('b2bKeyupClick', [ function () {
    return {
        restrict: 'A',
        link: function (scope, elem, attr) {
            var keyCode = [];
            attr.$observe('b2bKeyupClick', function (value) {
                if (value) {
                    keyCode = value.split(',');
                }
            });
            elem.bind('keydown keyup', function (ev) {
                var keyCodeCondition = function () {
                    var flag = false;
                    if (!(ev.keyCode)) {
                        if (ev.which) {
                            ev.keyCode = ev.which;
                        } else if (ev.charCode) {
                            ev.keyCode = ev.charCode;
                        }
                    }
                    if ((ev.keyCode && keyCode.indexOf(ev.keyCode.toString()) > -1)) {
                        flag = true;
                    }
                    return flag;
                };
                if (ev.type === 'keydown' && keyCodeCondition()) {
                    ev.preventDefault();
                }
                else if (ev.type === 'keyup' && keyCodeCondition()) {
                    elem[0].click();
                }
            });
        }
    };
}])

.factory('b2bDOMHelper', function() {

    var _isTabable = function(node) {
        var element = angular.element(node);
        var tagName = element[0].tagName.toUpperCase();

        if (isHidden(element)) {
            return false;
        }
        if (element.attr('tabindex') !== undefined) {
            return (parseInt(element.attr('tabindex'), 10) >= 0);
        }
        if (tagName === 'A' || tagName === 'AREA' || tagName === 'BUTTON' || tagName === 'INPUT' || tagName === 'TEXTAREA' || tagName === 'SELECT') {
            if (tagName === 'A' || tagName === 'AREA') {
                // anchors/areas without href are not focusable
                return (element[0].href !== '');
            }
            return !(element[0].disabled);
        }
        return false;
    };

    function isValidChild(child) {
        return child.nodeType == 1 && child.nodeName != 'SCRIPT' && child.nodeName != 'STYLE';
    }

    function isHidden(obj) {
        var elem = angular.element(obj);
        var elemStyle = undefined;
        if(obj instanceof HTMLElement){
            elemStyle = window.getComputedStyle(obj);
        }
        else {
            elemStyle = window.getComputedStyle(obj[0]);
        }
        return elem.hasClass('ng-hide') || elem.css('display') === 'none' || elemStyle.display === 'none' || elemStyle.visibility === 'hidden' || elem.css('visibility') === 'hidden';
    }

    function hasValidParent(obj) {
        return (isValidChild(obj) && obj.parentElement.nodeName !== 'BODY');
    }

    function traverse(obj, fromTop) {
        var obj = obj || document.getElementsByTagName('body')[0];
        if (isValidChild(obj) && _isTabable(obj)) {
            return obj;
        }
        // If object is hidden, skip it's children
        if (isValidChild(obj) && isHidden(obj)) {
            return undefined;
        }
        // If object is hidden, skip it's children
        if (angular.element(obj).hasClass('ng-hide')) {
            return undefined;
        }
        if (obj.hasChildNodes()) {
            var child;
            if (fromTop) {
                child = obj.firstChild;
            } else {
                child = obj.lastChild;
            }
            while(child) {
                var res =  traverse(child, fromTop);
                if(res){
                    return res;
                }
                else{
                    if (fromTop) {
                        child = child.nextSibling;
                    } else {
                        child = child.previousSibling;
                    }
                }
            }
        }
        else{
            return undefined;
        }
    }

    var _previousElement = function(el, isFocusable){

        var elem = el;
        if (el.hasOwnProperty('length')) {
            elem = el[0];
        }

        var parent = elem.parentElement;
        var previousElem = undefined;

        if(isFocusable) {
            if (hasValidParent(elem)) {
                var siblings = angular.element(parent).children();
                if (siblings.length > 0) {
                    // Good practice to splice out the elem from siblings if there, saving some time.
                    // We allow for a quick check for jumping to parent first before removing.
                    if (siblings[0] === elem) {
                        // If we are looking at immidiate parent and elem is first child, we need to go higher
                        var e = _previousElement(angular.element(elem).parent(), isFocusable);
                        if (_isTabable(e)) {
                            return e;
                        }
                    } else {
                        // I need to filter myself and any nodes next to me from the siblings
                        var indexOfElem = Array.prototype.indexOf.call(siblings, elem);
                        siblings = Array.prototype.filter.call(siblings, function(item, itemIndex) {
                            if (!angular.equals(elem, item) && itemIndex < indexOfElem) {
                                return true;
                            }
                        });
                    }
                    // We need to search backwards
                    for (var i = 0; i <= siblings.length-1; i++) {//for (var i = siblings.length-1; i >= 0; i--) {
                        var ret = traverse(siblings[i], false);
                        if (ret !== undefined) {
                            return ret;
                        }
                    }

                    var e = _previousElement(angular.element(elem).parent(), isFocusable);
                    if (_isTabable(e)) {
                        return e;
                    }
                }
            }
        } else {
            var siblings = angular.element(parent).children();
            if (siblings.length > 1) {
                // Since indexOf is on Array.prototype and parent.children is a NodeList, we have to use call()
                var index = Array.prototype.indexOf.call(siblings, elem);
                previousElem = siblings[index-1];
            }
        }
        return previousElem;
    };

    var _lastTabableElement = function(el) {
        /* This will return the first tabable element from the parent el */
        var elem = el;
        if (el.hasOwnProperty('length')) {
            elem = el[0];
        }

        return traverse(elem, false);
    };

    var _firstTabableElement = function(el) {
        /* This will return the first tabable element from the parent el */
        var elem = el;
        if (el.hasOwnProperty('length')) {
            elem = el[0];
        }

        return traverse(elem, true);
    };

    var _isInDOM = function(obj) {
      return document.documentElement.contains(obj);
    }

    return {
        firstTabableElement: _firstTabableElement,
        lastTabableElement: _lastTabableElement,
        previousElement: _previousElement,
        isInDOM: _isInDOM,
        isTabable: _isTabable,
        isHidden: isHidden
    };
})

.factory('windowOrientation', ['$window', function ($window) {
    var _isPotrait = function () {
        if ($window.innerHeight > $window.innerWidth) {
            return true;
        } else {
            return false;
        }
    };
    var _isLandscape = function () {
        if ($window.innerHeight < $window.innerWidth) {
            return true;
        } else {
            return false;
        }
    };

    return {
        isPotrait: _isPotrait,
        isLandscape: _isLandscape
    };
}])
.directive('b2bNextElement', function() {
  return {
    restrict: 'A',
    transclude: false,
    link: function (scope, elem, attr, ctrls) {

        var keys = attr.b2bNextElement.split(',');

        elem.bind('keydown', function (e) {
            var nextElement = elem.next();
            if(e.keyCode == 39 || e.keyCode == 40){ // if e.keyCode in keys
                if(nextElement.length) {
                    e.preventDefault();
                    nextElement[0].focus();
                }
            }
        });
    }
  }
})

.directive('b2bAccessibilityClick', [function () {
    return {
        restrict: 'A',
        link: function (scope, elem, attr, ctrl) {
            var keyCode = [];
            attr.$observe('b2bAccessibilityClick', function (value) {
                if (value) {
                    keyCode = value.split(',');
                }
            });
            elem.bind('keydown keypress', function (ev) {
                var keyCodeCondition = function () {
                    var flag = false;
                    if (!(ev.keyCode)) {
                        if (ev.which) {
                            ev.keyCode = ev.which;
                        } else if (ev.charCode) {
                            ev.keyCode = ev.charCode;
                        }
                    }
                    if ((ev.keyCode && keyCode.indexOf(ev.keyCode.toString()) > -1)) {
                        flag = true;
                    }
                    return flag;
                };
                if (keyCode.length > 0 && keyCodeCondition()) {
                    elem[0].click();
                    ev.preventDefault();
                }
            });
        }
    };
}])

.directive('b2bReset', ['$compile', function ($compile) {
        return {
            restrict: 'A',
            require: ['?ngModel', 'b2bReset'],
            controller: ['$scope', function ($scope) {
                var resetButton = angular.element('<button type="button" class="reset-field" tabindex="-1" aria-label="Click to reset" aria-hidden="true" role="button"></button>');

                this.getResetButton = function () {
                    return resetButton;
                };
            }],
            link: function (scope, element, attrs, ctrls) {

                var ngModelCtrl = ctrls[0];
                var ctrl = ctrls[1];

                var resetButton = ctrl.getResetButton();


                resetButton.on('click', function () {
                    element[0].value = '';

                    if (ngModelCtrl) {
                        if (attrs.b2bReset) {
                            ngModelCtrl.$setViewValue(attrs.b2bReset);
                        } else {
                            ngModelCtrl.$setViewValue('');
                        }
                        element[0].value = ngModelCtrl.$viewValue;
                        ngModelCtrl.$render();
                        scope.$digest();
                    }
                    element[0].focus();
                    element[0].select();
                });

                var addResetButton = function () {
                    element.after(resetButton);
                    element.unbind('focus input', addResetButton);
                };

                element.bind('focus input', addResetButton);
            }
        };
    }])

.directive('b2bPrevElement', ['b2bDOMHelper', 'keymap', function (b2bDOMHelper, keymap) {
  return {
    restrict: 'A',
    transclude: false,
    link: function (scope, elem, attr) {

        elem.bind('keydown', function (e) {
            if(e.keyCode == 37 || e.keyCode == 38){
                var prev = b2bDOMHelper.previousElement(elem, false);
                if(prev !== undefined) {
                    e.preventDefault();
                    prev.focus();
                }
            }
        });
    }
  }
}])
/**
 * @param {integer} delay - Timeout before first and last focusable elements are found
 * @param {boolean} trigger - A variable on scope that will trigger refinding first/last focusable elements
 */
.directive('b2bTrapFocusInsideElement', ['$timeout', 'b2bDOMHelper', 'keymap', 'events', function ($timeout, b2bDOMHelper, keymap, events) {
    return {
        restrict: 'A',
        transclude: false,
        link: function (scope, elem, attr) {

            var delay = parseInt(attr.delay, 10) || 10;

            /* Before opening modal, find the focused element */
            var firstTabableElement = undefined,
                lastTabableElement = undefined;

            function init() {
                $timeout(function () {
                    firstTabableElement = b2bDOMHelper.firstTabableElement(elem);
                    lastTabableElement = b2bDOMHelper.lastTabableElement(elem);
                    angular.element(firstTabableElement).bind('keydown', firstTabableElementKeyhandler);
                    angular.element(lastTabableElement).bind('keydown', lastTabableElementKeyhandler);
                }, delay, false);
            }

            if (attr.trigger !== undefined) {
                scope.$watch('trigger', function() {
                    if (scope.trigger) {
                        init();
                    }
                });
            }

            var firstTabableElementKeyhandler = function(e) {
                if (!e.keyCode) {
                    e.keyCode = e.which;
                }
                if (e.keyCode === keymap.KEY.TAB && e.shiftKey) {
                    if (attr.trapFocusInsideElement === 'true') {
                        var temp = b2bDOMHelper.lastTabableElement(elem);
                        if (lastTabableElement !== temp) {
                            // Unbind keydown handler on lastTabableElement
                            angular.element(lastTabableElement).unbind('keydown', lastTabableElementKeyhandler);
                            lastTabableElement = temp;
                            angular.element(lastTabableElement).bind('keydown', lastTabableElementKeyhandler);
                        }
                    }
                    lastTabableElement.focus();
                    events.preventDefault(e);
                    events.stopPropagation(e);
                }
            };

            var lastTabableElementKeyhandler = function(e) {
                if (!e.keyCode) {
                    e.keyCode = e.which;
                }
                if (e.keyCode === keymap.KEY.TAB && !e.shiftKey) {
                    if (attr.trapFocusInsideElement === 'true') {
                        var temp = b2bDOMHelper.firstTabableElement(elem);
                        if (firstTabableElement !== temp) {
                            // Unbind keydown handler on firstTabableElement
                            angular.element(firstTabableElement).unbind('keydown', firstTabableElementKeyhandler);
                            firstTabableElement = temp;
                            angular.element(firstTabableElement).bind('keydown', firstTabableElementKeyhandler);
                        }
                    }
                    firstTabableElement.focus();
                    events.preventDefault(e);
                    events.stopPropagation(e);
                }
            };

            init();
        }
    };
}])

.factory('trapFocusInElement', ['$document', '$isElement', 'b2bDOMHelper', 'keymap', '$interval', function ($document, $isElement, b2bDOMHelper, keymap, $interval) {
    var elementStack = [];
    var stackHead = undefined;
    var stopInterval = undefined;
    var intervalRequired = false;
    var interval = 1000;
    var firstTabableElement, lastTabableElement;

    var trapKeyboardFocusInFirstElement = function (e) {
        if (!e.keyCode) {
            e.keyCode = e.which;
        }

        if (e.shiftKey === true && e.keyCode === keymap.KEY.TAB) {
            lastTabableElement[0].focus();
            e.preventDefault(e);
            e.stopPropagation(e);
        }

    };

    var trapKeyboardFocusInLastElement = function (e) {
        if (!e.keyCode) {
            e.keyCode = e.which;
        }

        if (e.shiftKey === false && e.keyCode === keymap.KEY.TAB) {
            firstTabableElement[0].focus();
            e.preventDefault(e);
            e.stopPropagation(e);
        }
    };

    var trapFocusInElement = function (flag, firstTabableElementParam, lastTabableElementParam) {
        var bodyElements = $document.find('body').children();

        firstTabableElement = firstTabableElementParam ? firstTabableElementParam : angular.element(b2bDOMHelper.firstTabableElement(stackHead));
        lastTabableElement = lastTabableElementParam ? lastTabableElementParam : angular.element(b2bDOMHelper.lastTabableElement(stackHead));

        if (flag) {
            for (var i = 0; i < bodyElements.length; i++) {
                if (bodyElements[i] !== stackHead[0]) {
                    bodyElements.eq(i).attr('aria-hidden', true);
                }
            }
            firstTabableElement.bind('keydown', trapKeyboardFocusInFirstElement);
            lastTabableElement.bind('keydown', trapKeyboardFocusInLastElement);
        } else {
            for (var j = 0; j < bodyElements.length; j++) {
                if (bodyElements[j] !== stackHead[0]) {
                    bodyElements.eq(j).removeAttr('aria-hidden');
                }
            }
            firstTabableElement.unbind('keydown', trapKeyboardFocusInFirstElement);
            lastTabableElement.unbind('keydown', trapKeyboardFocusInLastElement);
        }

        if (intervalRequired && flag) {
            stopInterval = $interval(function () {
                var firstTabableElementTemp = angular.element(b2bDOMHelper.firstTabableElement(stackHead));
                var lastTabableElementTemp = angular.element(b2bDOMHelper.lastTabableElement(stackHead));
                if (firstTabableElementTemp[0] !== firstTabableElement[0] || lastTabableElementTemp[0] !== lastTabableElement[0]) {
                    $interval.cancel(stopInterval);
                    stopInterval = undefined;
                    trapFocusInElement(false, firstTabableElement, lastTabableElement);
                    trapFocusInElement(true, firstTabableElementTemp, lastTabableElementTemp);
                }
            }, interval);
        } else {
            if (stopInterval) {
                $interval.cancel(stopInterval);
                stopInterval = undefined;
            }
        }
    };
    var toggleTrapFocusInElement = function (flag, element, intervalRequiredParam, intervalParam) {
        intervalRequired = intervalRequiredParam ? intervalRequiredParam : intervalRequired;
        interval = intervalParam ? intervalParam : interval;
        if (angular.isDefined(flag) && angular.isDefined(element)) {
            if (flag && angular.isUndefined(stackHead)) {
                stackHead = element;
                trapFocusInElement(flag);
            } else {
                if (flag) {
                    trapFocusInElement(false);
                    elementStack.push(stackHead);
                    stackHead = element;
                    trapFocusInElement(true);
                } else {
                    if (angular.isDefined(stackHead) && (stackHead[0] === element[0])) {
                        trapFocusInElement(false);
                        stackHead = elementStack.pop();
                        if (angular.isDefined(stackHead)) {
                            trapFocusInElement(true);
                        }
                    }
                }
            }
        } else {
            if (angular.isDefined(stackHead)) {
                trapFocusInElement(false, firstTabableElement, lastTabableElement);
                trapFocusInElement(true);
            }
        }
    };

    return toggleTrapFocusInElement;
}])
.factory('draggedElement', ['b2bUserAgent', function(b2bUserAgent){
    var draggedElement;
    var dragImage = null;
    var dragImageTransform = null;
    var imageDragged;
    var dragImageWebKitTransform = null,
    customDragImage = null,
    customDragImageX = null,
    customDragImageY = null;

    function isValidChild(child) {
        return child.nodeType == 1 && child.nodeName != 'SCRIPT' && child.nodeName != 'STYLE';
    }
    return {
        setElement: function(data){
            draggedElement = data;
        },
        setDraggedElement: function(element,x,y){
            customDragImage = element;
            customDragImageX = x;
            customDragImageY = y;
        },
        getDraggedElement: function() {
            this.createDragImage();
            return dragImage;
        },
        getElement: function(){
            return draggedElement;
        },
        duplicateStyle: function(srcNode, dstNode) {
            // Is this node an element?
            if (srcNode.nodeType == 1) {
              // Remove any potential conflict attributes
                dstNode.removeAttribute("id");
                dstNode.removeAttribute("draggable");

                // Clone the style
                var cs = window.getComputedStyle(srcNode);
                for (var i = 0; i < cs.length; i++) {
                    var csName = cs[i];
                    dstNode.style.setProperty(csName, cs.getPropertyValue(csName), cs.getPropertyPriority(csName));
                }

                // Pointer events as none makes the drag image transparent to document.elementFromPoint()
                dstNode.style.pointerEvents = "none";
            }

            // Do the same for the children
            if (srcNode.hasChildNodes()) {
              for (var j = 0; j < srcNode.childNodes.length; j++) {
                this.duplicateStyle(srcNode.childNodes[j], dstNode.childNodes[j]);
              }
            }

        },
        createDragImage: function(el) {
            if (customDragImage) {
                dragImage = customDragImage.cloneNode(true);
                this.duplicateStyle(customDragImage, dragImage);
            }
            dragImage.style.opacity = "1";
            dragImage.style.zIndex = "99999";
            dragImage.style.position = "absolute";
            dragImage.style.left = customDragImageX + "px";
            dragImage.style.top = customDragImageY + "px";
            if(b2bUserAgent.isIE){
                dragImage.style.width = customDragImage.offsetWidth + "px";
                dragImage.style.height = customDragImage.offsetHeight + "px";

                for (var k = 0; k < dragImage.childNodes.length; k++) {
                    if(isValidChild(dragImage.childNodes[k])){
                        dragImage.childNodes[k].style.width = customDragImage.childNodes[k].offsetWidth + "px";
                        dragImage.childNodes[k].style.height = customDragImage.childNodes[k].offsetHeight + "px";
                    }
                }
            }

            var transform = dragImage.style.transform;
            if (typeof transform !== "undefined") {
                dragImageTransform = "";
                if (transform != "none") {
                  dragImageTransform = transform.replace(/translate\(\D*\d+[^,]*,\D*\d+[^,]*\)\s*/g, '');
                }
            }
        }
    }
}])

.directive('accessibilityDragdrop', ['draggedElement', '$timeout', function (draggedElement,$timeout) {
    return {
        restrict: 'A',
        link: function (scope, elem, attr, ctrl) {
            elem.bind('click', function(e) {
                var element = scope.tableData[attr.currentElement];
                var currentElement = this.closest('tr');
                currentElement.classList.add('b2b-drag-element');

                scope.tableData.splice(attr.currentElement, 1);
                scope.tableData.splice(attr.dropElement, 0, element);

                var offscreenText = this.closest('table').previousSibling;
                angular.element(offscreenText).html('Row ' + attr.currentElement + ' moved to ' + attr.dropElement);

                $timeout(function(){
                    currentElement.classList.remove('b2b-drag-element');
                }, 1000);

                currentElement.focus();
            });
        }
    };
}])

.directive('draggable', ['draggedElement','b2bUserAgent', function (draggedElement, b2bUserAgent) {
    return function(scope, element) {
        element[0].draggable = true;

        element.bind('dragstart', function(e) {
            draggedElement.setElement(this.parentElement.parentElement);
            if(e.pageX === undefined || e.pageY === undefined) {
                draggedElement.setDraggedElement(this.parentElement.parentElement,e.originalEvent.pageX,e.originalEvent.pageY);
            } else {
                draggedElement.setDraggedElement(this.parentElement.parentElement,e.pageX,e.pageY);
            }

            if (e.originalEvent) {
                 e.dataTransfer = e.originalEvent.dataTransfer;
            }
            e.dataTransfer.effectAllowed = 'move';

            if (!b2bUserAgent.isIE() ){
                dragGhost = this.cloneNode(true);
                dragGhost.classList.add('hidden-drag-ghost');
                document.body.appendChild(dragGhost);
                e.dataTransfer.setDragImage(dragGhost, 0, 0);
            }
            this.parentElement.parentElement.classList.add('b2b-drag-element');
            if(e instanceof jQuery || e instanceof jQuery.Event ) {
                e.result = true;
            }
            return true;
        });
        draggedElement.addEventListener('dragstart', function(e){
            e.dataTransfer.setData('text', 'foo');
          });

        element.bind('dragend', function(e) {
            draggedElement.setElement(e);
            this.parentElement.parentElement.classList.remove('b2b-drag-element');
            return true;
        });
    };
}])

.directive('droppable', ['draggedElement', 'b2bUserAgent', '$document', '$timeout', function (draggedElement, b2bUserAgent, $document, $timeout) {
    return {
        restrict: 'EA',
        replace: true,
        scope: {
          rowData: '='
        },
        link: function(scope, element, attr) {
            var elementDragged = false;
            var handleDrag = function(e) {
                if(e.pageX === undefined || e.pageY === undefined) {
                    ghostObject.style.left = (e.originalEvent.pageX >> 0) + "px";
                    ghostObject.style.top = (e.originalEvent.pageY >> 0) + "px";
                } else {
                    ghostObject.style.left = (e.pageX >> 0) + "px";
                    ghostObject.style.top = (e.pageY >> 0) + "px";
                }
            };
            var removeGhostElement = function(parentElement) {
                if(dragged){
                    var elementToRemove = parentElement.firstChild;
                    parentElement.removeChild(elementToRemove);
                    dragged = false;
                }
            };
            var handleMouseUp = function(parentElement) {
                if(dragged){
                    removeGhostElement(parentElement.data)
                    dragged = false;
                }
                $document.unbind('mousemove', handleMouseUp);
                $document.unbind('dragover', handleDrag);
            };

            if(attr.droppable === 'true') {
              var ghostElement;
              element.bind('dragstart', function(e) {
                if(e.target.classList !== undefined && e.target.classList.contains('b2b-draggable')) {
                    var count = 0;
                    ghostElement =  draggedElement.getDraggedElement();
                    var parent = this.parentElement;
                    parent.insertBefore(ghostElement,parent.firstChild);
                    ghostObject = parent.firstChild;
                    dragged = true;
                    $document.bind('dragover', handleDrag);
                    if(!b2bUserAgent.isFF()) {
                        $document.bind('mousemove', function() {
                            handleMouseUp({data: parent});
                        });
                    } else {
                        var checkGhostIsLoaded = setInterval(function(){
                            if(elementDragged == false){
                                count++;
                            }
                            if(elementDragged == false && count == 2){
                                handleMouseUp({data: parent});
                                clearInterval(checkGhostIsLoaded);
                            }
                            elementDragged = false;
                        },500)
                    }

                } else {
                    e.preventDefault();
                }
                return true;
              });

              element.bind('dragover', function(e) {
                if (e.originalEvent) {
                     e.dataTransfer = e.originalEvent.dataTransfer;
                }
                e.dataTransfer.dropEffect = 'move';
                this.classList.add('b2b-drag-over');

                if(!ghostElement || ghostElement === undefined) {
                    ghostElement =  draggedElement.getDraggedElement();
                }

                if (e.preventDefault) e.preventDefault(); // allows us to drop
                return true;
              });

              element.bind('dragenter', function(e) {
                if(!ghostElement || ghostElement === undefined) {
                    ghostElement =  draggedElement.getDraggedElement();
                }
                if(e.target.getAttribute('droppable') ==='true') {
                    this.click();
                }
                return true;
              });

              element.bind('dragleave', function(e) {
                this.classList.remove('b2b-drag-over');
                return true;
              });

              element.bind('drop', function(e) {
                if(this.parentElement.nodeName === 'TBODY') {
                    removeGhostElement(this.parentElement);
                } else if(this.offsetParent.getElementsByTagName('tbody').length > 0 && this.offsetParent.getElementsByTagName('tbody')[0].nodeName === 'TBODY') {
                    removeGhostElement(this.offsetParent.getElementsByTagName('tbody')[0]);
                }
                var ele = draggedElement.getElement();
                if (e.stopPropagation) e.stopPropagation();
                if (e.preventDefault) e.preventDefault();
                this.classList.remove('b2b-drag-over');

                if(ele && ele.hasAttribute('data-index')){
                    if(scope.rowData){
                        var element = scope.rowData[parseInt(ele.getAttribute('data-index'))];
                    }
                    if(element !== undefined) {
                        scope.rowData.splice(parseInt(ele.getAttribute('data-index')), 1);
                        scope.rowData.splice(parseInt(e.currentTarget.getAttribute('data-index')), 0 , element);
                    }
                }
                scope.$apply();

                return true;
              });

              element.bind('dragend', function(e) {
                removeGhostElement(this.parentElement);

                this.classList.remove('b2b-drag-over');
                return true;
              });

              window.addEventListener('dragover', function () {
                elementDragged = true;
              });
          }
        }
    }
}])
.directive('b2bSetNextFocusOn', ['b2bDOMHelper', '$timeout', function(b2bDOMHelper, $timeout) {
    return {
        restrict: 'A',
        scope: true,
        link: function (scope, elem, attr) {
            elem.bind('click', function(){
                var firstFocusableElement = undefined;
                var containerElem = undefined;
                var containerArray = [];
                var timeout = parseInt(attr.setNextFocusTimeout, 0) | 100;
                var index = parseInt(attr.b2bSetNextFocusIndex, 0) | 0;

                 /*
                  *Fix for IE7 and lower
                  *polyfill src: https://github.com/HubSpot/pace/issues/102
                  */
                if (!document.querySelectorAll) {
                    document.querySelectorAll = function (selectors) {
                        var style = document.createElement('style'), elements = [], element;
                        document.documentElement.firstChild.appendChild(style);
                        document._qsa = [];

                        style.styleSheet.cssText = selectors + '{x-qsa:expression(document._qsa && document._qsa.push(this))}';
                        window.scrollBy(0, 0);
                        style.parentNode.removeChild(style);

                        while (document._qsa.length) {
                            element = document._qsa.shift();
                            element.style.removeAttribute('x-qsa');
                            elements.push(element);
                        }
                        document._qsa = null;
                        return elements;
                    };
                }

                if (attr.b2bSetNextFocusOn === '') {
                    return;
                } else {
                    containerArray = attr.b2bSetNextFocusOn.split(' ');
                }
                $timeout(function(){
                    var i = 0;
                    do { // cycles thru containerArray until finds a match in DOM to set focus to
                        containerElem = document.querySelectorAll(containerArray[i])[index];
                        i++;
                    } while ( (!containerElem) && (i < containerArray.length) );
                    if(containerElem){
                        if (!angular.isDefined(firstFocusableElement)) {
                            firstFocusableElement = b2bDOMHelper.firstTabableElement(containerElem);
                        }
                        firstFocusableElement.focus();
                    }
                }, timeout, false)
            });
        }

    };
}])

.directive('b2bInputAllow', ['keymap', function(keymap) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, elem, attr, ctrl) {
            var regexExpression = null;
            attr.$observe('b2bInputAllow', function (value) {
                if (value) {
                    regexExpression = new RegExp(value);
                }
            });
            var isValid = function(str) {
                if (regexExpression !== null) {
                    return regexExpression.test(str);
                }
                return false;
            };
            elem.bind('keypress', function($event) {
                var charcode = String.fromCharCode($event.which || $event.keyCode);
                 if (!isValid(charcode) && !keymap.isAllowedKey($event.which || $event.keyCode) && $event.keyCode !== keymap.KEY.TAB) {
                    $event.preventDefault();
                    $event.stopPropagation();
                }
            });
            elem.bind('input', function (evt) {
                var inputString = ctrl.$viewValue;
                if (isValid(inputString)) {
                    ctrl.$setViewValue(inputString);
                    ctrl.$render();
                    scope.$apply();
                }
            });
        }
    };
}])

.directive('b2bInputDeny', [function() {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, elem, attr, ctrl) {
            var regexExpression = null;
            attr.$observe('b2bInputDeny', function (value) {
                if (value) {
                    regexExpression = new RegExp(value, 'g');
                }
            });
            elem.bind('input', function () {
                var inputString = ctrl.$viewValue && ctrl.$viewValue.replace(regexExpression, '');
                if (inputString !== ctrl.$viewValue) {
                    ctrl.$setViewValue(inputString);
                    ctrl.$render();
                    scope.$apply();
                }
            });
        }
    };
}])

.directive('b2bDragonInput', [function() {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, elem, attr, ctrl) {
            elem.on('focus keyup', function(){
                elem.triggerHandler('change');
            });
        }
    };
}])

.directive('b2bKey', ['b2bUtilitiesConfig', '$timeout', 'keymap', function (b2bUtilitiesConfig, $timeout, keymap) {
    return {
        restrict: 'EA',
        controller: ['$scope', '$element', '$attrs', function ($scope, $element,attr) {
            this.childElements = [];
            this.disableNodes = {};
            this.enableSearch = attr.enableSearch !== undefined ? true : b2bUtilitiesConfig.enableSearch;
            this.circularTraversal = attr.circularTraversal !== undefined ? true : b2bUtilitiesConfig.circularTraversal;
            this.counter = -1;
            if (this.enableSearch) {
                this.searchKeys = [];
            }
            var searchString = '';

            var selfCtrl = this;

            this.childElementsList = [];

            this.b2bKeyID = "";

            if (angular.isDefined(attr.b2bKey)) {
                this.b2bKeyID = attr.b2bKey;
            }

            this.calculateChildElementsList = function () {
                return $element[0].querySelectorAll("[b2b-key-item='" + this.b2bKeyID + "']:not([disabled])");
            };

            this.resetChildElementsList = function () {
                return $timeout(function () {
                    selfCtrl.childElementsList = selfCtrl.calculateChildElementsList();
                });
            };

            this.resetChildElementsList();

            $scope.$on('b2b-key-reset-child-elements-list', function () {
                selfCtrl.resetChildElementsList();
            });


            this.registerElement = function (childElement, searchKey) {
                this.childElements.push(childElement);
                if (this.enableSearch) {
                    this.searchKeys.push(searchKey);
                }
                var count = this.childElements.length - 1;
                this.maxLength = count + 1;
                return count;
            };
            this.toggleDisable = function (count, state) {
                this.disableNodes[count] = state;
            };
            this.searchElement = function (searchExp) {
                var regex = new RegExp("\\b" + searchExp, "gi");
                if (angular.isDefined(attr.containsSearch) && attr.containsSearch === "false") {
                    regex = new RegExp("^" + searchExp, "i");
                }
                var position = this.searchKeys.regexIndexOf(regex, this.counter + 1, true);
                if (position > -1) {
                    this.counter = position;
                    this.moveFocus(this.counter);
                }
            };
            this.startTimer = function (time) {
                if (searchString === '') {
                    $timeout(function () {
                        searchString = '';
                    }, time);
                }
            };
            this.resetCounter = function (count) {
                this.counter = count;
            };
            this.moveNext = function (count) {
                this.counter = (this.counter + count) < this.maxLength ? this.counter + count : (this.circularTraversal ? 0 : this.counter);
                if (this.disableNodes[this.counter]) {
                    if ((this.counter + count) < this.maxLength) {
                        this.moveNext(count);
                    }
                } else {
                    this.moveFocus(this.counter);
                }
            };
            this.movePrev = function (count) {
                this.counter = (this.counter - count) > -1 ? this.counter - count : (this.circularTraversal ? this.maxLength-1 : this.counter);
                if (this.disableNodes[this.counter]) {
                    if ((this.counter - count) > -1) {
                        this.movePrev(count);
                    }
                } else {
                    this.moveFocus(this.counter);
                }
            };
            this.moveFocus = function (index) {
                this.childElements[index][0].focus();
            };

            this.keyDownHandler = function (ev, count) {
                if (angular.isDefined(count) && !isNaN(count) && count !== this.counter) {
                    this.resetCounter(count);
                }
                if (!ev.keyCode) {
                    if (ev.which) {
                        ev.keyCode = ev.which;
                    } else if (ev.charCode) {
                        ev.keyCode = ev.charCode;
                    }
                }
                if (ev.keyCode) {
                    if (this.prev && this.prev.indexOf(ev.keyCode.toString()) > -1) {
                        this.movePrev(1);
                        ev.preventDefault();
                        ev.stopPropagation();
                    } else if (this.next && this.next.indexOf(ev.keyCode.toString()) > -1) {
                        this.moveNext(1);
                        ev.preventDefault();
                        ev.stopPropagation();
                    } else if (this.up && this.up.indexOf(ev.keyCode.toString()) > -1) {
                        if (this.type === 'table') {
                            this.movePrev(this.columns);
                            ev.preventDefault();
                            ev.stopPropagation();
                        }
                    } else if (this.down && this.down.indexOf(ev.keyCode.toString()) > -1) {
                        if (this.type === 'table') {
                            this.moveNext(this.columns);
                            ev.preventDefault();
                            ev.stopPropagation();
                        }
                    } else if (ev.keyCode === keymap.KEY.HOME) {
                        var firstIndex = 0;
                        while (this.disableNodes[firstIndex] !== false) {
                            firstIndex++;
                        };
                        var count = this.counter - firstIndex;
                        this.movePrev(count);
                        ev.preventDefault();
                        ev.stopPropagation();
                    } else if (ev.keyCode === keymap.KEY.END) {
                        var lastIndex = this.childElements.length - 1;
                        while (this.disableNodes[lastIndex] !== false) {
                            lastIndex--;
                        };
                        var count = lastIndex - this.counter;
                        this.moveNext(count);
                        ev.preventDefault();
                        ev.stopPropagation();
                    } else if (ev.keyCode >= 48 && ev.keyCode <= 105) {
                        if (this.enableSearch) {
                            this.startTimer(b2bUtilitiesConfig.searchTimer);
                            searchString = searchString + (keymap.MAP[ev.keyCode] || '');
                            this.searchElement(searchString);
                            ev.preventDefault();
                            ev.stopPropagation();
                        }
                    }
                }
            };
        }],
        link: function (scope, elem, attr, ctrl) {
            ctrl.prev = attr.prev ? attr.prev.split(',') : b2bUtilitiesConfig.prev.split(',');
            ctrl.next = attr.next ? attr.next.split(',') : b2bUtilitiesConfig.next.split(',');
            ctrl.type = attr.type ? attr.type : b2bUtilitiesConfig.type;
            if (ctrl.type === 'table') {
                ctrl.up = attr.up ? attr.up.split(',') : b2bUtilitiesConfig.up.split(',');
                ctrl.down = attr.down ? attr.down.split(',') : b2bUtilitiesConfig.down.split(',');
                ctrl.columns = attr.columns ? parseInt(attr.columns, 10) : b2bUtilitiesConfig.columns;
            }

            elem.bind('keydown', function (ev) {
                ctrl.keyDownHandler(ev);
            });
        }
    };
}])

.directive('b2bKeyItem', [function () {
    return {
        restrict: 'EA',
        link: function (scope, elem, attr, ctrl) {
            var parentCtrl = (elem.parent() && elem.parent().controller('b2bKey')) || undefined;
            if (angular.isDefined(parentCtrl)) {
                var count = parentCtrl.registerElement(elem, attr.searchKey);
                elem.bind('keydown', function (ev) {
                    parentCtrl.keyDownHandler(ev, count);
                });
                scope.$watch(attr.b2bKeyItem, function (value) {
                    value = value === undefined ? true : value;
                    parentCtrl.toggleDisable(count, !value);
                });
                scope.$on('$destroy', function () {
                    parentCtrl.toggleDisable(count, true);
                });
            }
        }
    };
}])

.directive('b2bElementFocus', [function () {
    return {
        restrict: 'A',
        link: function (scope, elem, attr, ctrl) {
            scope.$watch(attr.b2bElementFocus, function (value) {
                if (value === true) {
                    elem[0].focus();
                }
            });
        }
    };
}])


.directive('b2bAppendElement', ['$compile', function ($compile) {
    return {
        restrict: 'A',
        link: function (scope, elem, attr, ctrl) {
            var parameters = attr.b2bAppendElement.split(':');
            if (parameters.length === 1) {
                elem.append(scope.$eval(parameters[0]));
            } else if (parameters.length === 2) {
                if (parameters[1] === 'compile') {
                    var element = angular.element('<span>' + scope.$eval(parameters[0]) + '</span>');
                    elem.append($compile(element)(scope));
                }
            }

        }
    };
}])

.directive('b2bKeyItemRefreshInNgRepeat', [function () {
    return {
        restrict: 'EA',
        require: '^^b2bKey',
        link: function (scope, elem, attr, parentCtrl) {
            if (angular.isDefined(parentCtrl)) {

                var attrToObserve = 'attrToObserve';

                if (attr.b2bKeyItemRefreshInNgRepeat) {
                    attrToObserve = 'b2bKeyItemRefreshInNgRepeat';
                }

                attr.$observe(attrToObserve, function (newVal, oldVal) {
                    if (newVal && newVal !== oldVal) {
                        parentCtrl.resetChildElementsList();
                    }
                });
            }
        }
    };
}])

.constant('b2bMaskConfig', {
    maskDefinitions: {
        '9': /\d/,
        'A': /[a-zA-Z]/,
        '*': /[a-zA-Z0-9]/
    },
    clearOnBlur: false,
    clearOnBlurPlaceholder: false,
    escChar: '\\',
    eventsToHandle: ['input', 'keyup', 'click', 'focus'],
    addDefaultPlaceholder: true,
    allowInvalidValue: true
})
/**
 * @param {boolean} modelViewValue - If this is set to true, then the model value bound with ng-model will be the same as the $viewValue meaning it will contain any static mask characters present in the mask definition. This will not set the model value to a $viewValue that is considered invalid.
 * @param {String} maskPlaceholder - Allows customizing the mask placeholder when a user has focused the input element and while typing in their value
 * @param {String} maskPlaceholderChar - Allows customizing the mask placeholder character. The default mask placeholder is _.
 * @param {boolean} addDefaultPlaceholder - The default placeholder is constructed from the ui-mask definition so a mask of 999-9999 would have a default placeholder of ___-____; unless you have overridden the default placeholder character.
 */
.directive('b2bMask', ['b2bMaskConfig', function(b2bMaskConfig) {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function(scope, element, attrs, ctrl) {
            var maskProcessed = false, eventsBound = false,
                maskCaretMap, maskPatterns, maskPlaceholder, maskComponents,
                // Minimum required length of the value to be considered valid
                minRequiredLength,
                value, valueMasked, isValid,
                // Vars for initializing/uninitializing
                originalPlaceholder = attrs.placeholder,
                originalMaxlength = attrs.maxlength,
                // Vars used exclusively in eventHandler()
                oldValue, oldValueUnmasked, oldCaretPosition, oldSelectionLength,
                // Used for communicating if a backspace operation should be allowed between
                // keydownHandler and eventHandler
                preventBackspace;

            var options = b2bMaskConfig;

            function isFocused (elem) {
              return elem === document.activeElement && (!document.hasFocus || document.hasFocus()) && !!(elem.type || elem.href || ~elem.tabIndex);
            }

            var originalIsEmpty = ctrl.$isEmpty;
            ctrl.$isEmpty = function(value) {
                if (maskProcessed) {
                    return originalIsEmpty(unmaskValue(value || ''));
                } else {
                    return originalIsEmpty(value);
                }
            };

            function initialize(maskAttr) {
                if (!angular.isDefined(maskAttr)) {
                    return uninitialize();
                }
                processRawMask(maskAttr);
                if (!maskProcessed) {
                    return uninitialize();
                }
                initializeElement();
                bindEventListeners();
                return true;
            }

            function initPlaceholder(placeholderAttr) {
                if ( ! placeholderAttr) {
                    return;
                }
                maskPlaceholder = placeholderAttr;
                /* If the mask is processed, then we need to update the value
                   but don't set the value if there is nothing entered into the element
                   and there is a placeholder attribute on the element because that
                   will only set the value as the blank maskPlaceholder
                   and override the placeholder on the element */
                if (maskProcessed && !(element.val().length === 0 && angular.isDefined(attrs.placeholder))) {
                    element.val(maskValue(unmaskValue(element.val())));
                }
            }

            function initPlaceholderChar() {
                return initialize(attrs.uiMask);
            }

            var modelViewValue = false;

            attrs.$observe('modelViewValue', function(val) {
                if (val === 'true') {
                    modelViewValue = true;
                }
            });

            attrs.$observe('allowInvalidValue', function(val) {
                linkOptions.allowInvalidValue = val === ''? true : !!val;
                formatter(ctrl.$modelValue);
            });

            function formatter(fromModelValue) {
                if (!maskProcessed) {
                    return fromModelValue;
                }
                value = unmaskValue(fromModelValue || '');
                isValid = validateValue(value);
                ctrl.$setValidity('mask', isValid);

                if (!value.length) return undefined;
                if (isValid || linkOptions.allowInvalidValue) {
                    return maskValue(value);
                } else {
                    return undefined;
                }
            }

            function parser(fromViewValue) {
                if (!maskProcessed) {
                    return fromViewValue;
                }
                value = unmaskValue(fromViewValue || '');
                isValid = validateValue(value);
                /* We have to set viewValue manually as the reformatting of the input
                   value performed by eventHandler() doesn't happen until after
                   this parser is called, which causes what the user sees in the input
                   to be out-of-sync with what the ctrl's $viewValue is set to. */
                ctrl.$viewValue = value.length ? maskValue(value) : '';
                ctrl.$setValidity('mask', isValid);

                if (isValid || linkOptions.allowInvalidValue) {
                    return modelViewValue ? ctrl.$viewValue : value;
                }
            }

            var linkOptions = {};

            // to do
            if (attrs.b2bMaskOptions) {
                linkOptions = scope.$eval('[' + attrs.b2bMaskOptions + ']');
                if (angular.isObject(linkOptions[0])) {
                    // we can't use angular.copy nor angular.extend, they lack the power to do a deep merge
                    linkOptions = (function(original, current) {
                        for (var i in original) {
                            if (Object.prototype.hasOwnProperty.call(original, i)) {
                                if (current[i] === undefined) {
                                    current[i] = angular.copy(original[i]);
                                } else {
                                    if (angular.isObject(current[i]) && !angular.isArray(current[i])) {
                                        current[i] = angular.extend({}, original[i], current[i]);
                                    }
                                }
                            }
                        }
                        return current;
                    })(options, linkOptions[0]);
                } else {
                    linkOptions = options;  //gotta be a better way to do this..
                }
            } else {
                linkOptions = options;
            }

            attrs.$observe('b2bMask', initialize);
            if (angular.isDefined(attrs.maskPlaceholder)) {
                attrs.$observe('maskPlaceholder', initPlaceholder);
            }
            else {
                attrs.$observe('placeholder', initPlaceholder);
            }
            if (angular.isDefined(attrs.maskPlaceholderChar)) {
                attrs.$observe('maskPlaceholderChar', initPlaceholderChar);
            }

            ctrl.$formatters.unshift(formatter);
            ctrl.$parsers.unshift(parser);

            function uninitialize() {
                maskProcessed = false;
                unbindEventListeners();

                if (angular.isDefined(originalPlaceholder)) {
                    element.attr('placeholder', originalPlaceholder);
                } else {
                    element.removeAttr('placeholder');
                }

                if (angular.isDefined(originalMaxlength)) {
                    element.attr('maxlength', originalMaxlength);
                } else {
                    element.removeAttr('maxlength');
                }

                element.val(ctrl.$modelValue);
                ctrl.$viewValue = ctrl.$modelValue;
                return false;
            }

            function initializeElement() {
                value = oldValueUnmasked = unmaskValue(ctrl.$modelValue || '');
                valueMasked = oldValue = maskValue(value);
                isValid = validateValue(value);
                if (attrs.maxlength) { // Double maxlength to allow pasting new val at end of mask
                    element.attr('maxlength', maskCaretMap[maskCaretMap.length - 1] * 2);
                }
                if ( ! originalPlaceholder && linkOptions.addDefaultPlaceholder) {
                    element.attr('placeholder', maskPlaceholder);
                }
                var viewValue = ctrl.$modelValue;
                var idx = ctrl.$formatters.length;
                while(idx--) {
                    viewValue = ctrl.$formatters[idx](viewValue);
                }
                ctrl.$viewValue = viewValue || '';
                ctrl.$render();
            }

            function bindEventListeners() {
                if (eventsBound) {
                    return;
                }
                element.bind('blur', blurHandler);
                element.bind('mousedown mouseup', mouseDownUpHandler);
                element.bind('keydown', keydownHandler);
                element.bind(linkOptions.eventsToHandle.join(' '), eventHandler);
                eventsBound = true;
            }

            function unbindEventListeners() {
                if (!eventsBound) {
                    return;
                }
                element.unbind('blur', blurHandler);
                element.unbind('mousedown', mouseDownUpHandler);
                element.unbind('mouseup', mouseDownUpHandler);
                element.unbind('keydown', keydownHandler);
                element.unbind('input', eventHandler);
                element.unbind('keyup', eventHandler);
                element.unbind('click', eventHandler);
                element.unbind('focus', eventHandler);
                eventsBound = false;
            }

            function validateValue(value) {
                // Zero-length value validity is ngRequired's determination
                return value.length ? value.length >= minRequiredLength : true;
            }

             function unmaskValue(value) {
                var valueUnmasked = '',
                    input = element[0],
                    maskPatternsCopy = maskPatterns.slice(),
                    selectionStart = oldCaretPosition,
                    selectionEnd = selectionStart + getSelectionLength(input),
                    valueOffset, valueDelta, tempValue = '';
                // Preprocess by stripping mask components from value
                value = value.toString();
                valueOffset = 0;
                valueDelta = value.length - maskPlaceholder.length;
                angular.forEach(maskComponents, function(component) {
                    var position = component.position;
                    //Only try and replace the component if the component position is not within the selected range
                    //If component was in selected range then it was removed with the user input so no need to try and remove that component
                    if (!(position >= selectionStart && position < selectionEnd)) {
                        if (position >= selectionStart) {
                            position += valueDelta;
                        }
                        if (value.substring(position, position + component.value.length) === component.value) {
                            tempValue += value.slice(valueOffset, position);// + value.slice(position + component.value.length);
                            valueOffset = position + component.value.length;
                        }
                    }
                });
                value = tempValue + value.slice(valueOffset);
                angular.forEach(value.split(''), function(chr) {
                    if (maskPatternsCopy.length && maskPatternsCopy[0].test(chr)) {
                        valueUnmasked += chr;
                        maskPatternsCopy.shift();
                    }
                });

                return valueUnmasked;
            }

            function maskValue(unmaskedValue) {
                var valueMasked = '',
                        maskCaretMapCopy = maskCaretMap.slice();

                angular.forEach(maskPlaceholder.split(''), function(chr, i) {
                    if (unmaskedValue.length && i === maskCaretMapCopy[0]) {
                        valueMasked += unmaskedValue.charAt(0) || '_';
                        unmaskedValue = unmaskedValue.substr(1);
                        maskCaretMapCopy.shift();
                    }
                    else {
                        valueMasked += chr;
                    }
                });
                return valueMasked;
            }

            function getPlaceholderChar(i) {
                var placeholder = angular.isDefined(attrs.uiMaskPlaceholder) ? attrs.uiMaskPlaceholder : attrs.placeholder,
                    defaultPlaceholderChar;

                if (angular.isDefined(placeholder) && placeholder[i]) {
                    return placeholder[i];
                } else {
                    defaultPlaceholderChar = angular.isDefined(attrs.uiMaskPlaceholderChar) && attrs.uiMaskPlaceholderChar ? attrs.uiMaskPlaceholderChar : '_';
                    return (defaultPlaceholderChar.toLowerCase() === 'space') ? ' ' : defaultPlaceholderChar[0];
                }
            }

            /* Generate array of mask components that will be stripped from a masked value
               before processing to prevent mask components from being added to the unmasked value.
               E.g., a mask pattern of '+7 9999' won't have the 7 bleed into the unmasked value. */
            function getMaskComponents() {
                var maskPlaceholderChars = maskPlaceholder.split(''),
                        maskPlaceholderCopy, components;

                /* maskCaretMap can have bad values if the input has the ui-mask attribute implemented as an obversable property, e.g. the demo page */
                if (maskCaretMap && !isNaN(maskCaretMap[0])) {
                    /* Instead of trying to manipulate the RegEx based on the placeholder characters
                       we can simply replace the placeholder characters based on the already built
                       maskCaretMap to underscores and leave the original working RegEx to get the proper
                       mask components */
                    angular.forEach(maskCaretMap, function(value) {
                        maskPlaceholderChars[value] = '_';
                    });
                }
                maskPlaceholderCopy = maskPlaceholderChars.join('');
                components = maskPlaceholderCopy.replace(/[_]+/g, '_').split('_');
                components = components.filter(function(s) {
                    return s !== '';
                });

                /* need a string search offset in cases where the mask contains multiple identical components
                   E.g., a mask of 99.99.99-999.99 */
                var offset = 0;
                return components.map(function(c) {
                    var componentPosition = maskPlaceholderCopy.indexOf(c, offset);
                    offset = componentPosition + 1;
                    return {
                        value: c,
                        position: componentPosition
                    };
                });
            }

            function processRawMask(mask) {
                var characterCount = 0;

                maskCaretMap = [];
                maskPatterns = [];
                maskPlaceholder = '';

                if (angular.isString(mask)) {
                    minRequiredLength = 0;

                    var isOptional = false,
                            numberOfOptionalCharacters = 0,
                            splitMask = mask.split('');

                    var inEscape = false;
                    angular.forEach(splitMask, function(chr, i) {
                        if (inEscape) {
                            inEscape = false;
                            maskPlaceholder += chr;
                            characterCount++;
                        }
                        else if (linkOptions.escChar === chr) {
                            inEscape = true;
                        }
                        else if (linkOptions.maskDefinitions[chr]) {
                            maskCaretMap.push(characterCount);

                            maskPlaceholder += getPlaceholderChar(i - numberOfOptionalCharacters);
                            maskPatterns.push(linkOptions.maskDefinitions[chr]);

                            characterCount++;
                            if (!isOptional) {
                                minRequiredLength++;
                            }

                            isOptional = false;
                        }
                        else if (chr === '?') {
                            isOptional = true;
                            numberOfOptionalCharacters++;
                        }
                        else {
                            maskPlaceholder += chr;
                            characterCount++;
                        }
                    });
                }
                // Caret position immediately following last position is valid.
                maskCaretMap.push(maskCaretMap.slice().pop() + 1);

                maskComponents = getMaskComponents();
                maskProcessed = maskCaretMap.length > 1 ? true : false;
            }

            var prevValue = element.val();
            function blurHandler() {
                if (linkOptions.clearOnBlur || ((linkOptions.clearOnBlurPlaceholder) && (value.length === 0) && attrs.placeholder)) {
                    oldCaretPosition = 0;
                    oldSelectionLength = 0;
                    if (!isValid || value.length === 0) {
                        valueMasked = '';
                        element.val('');
                        scope.$apply(function() {
                            //only $setViewValue when not $pristine to avoid changing $pristine state.
                            if (!ctrl.$pristine) {
                                ctrl.$setViewValue('');
                            }
                        });
                    }
                }
                //Check for different value and trigger change.
                if (value !== prevValue) {
                    var currentVal = element.val();
                    var isTemporarilyEmpty = value === '' && currentVal && angular.isDefined(attrs.uiMaskPlaceholderChar) && attrs.uiMaskPlaceholderChar === 'space';
                    if(isTemporarilyEmpty) {
                        element.val('');
                    }
                    triggerChangeEvent(element[0]);
                    if(isTemporarilyEmpty) {
                        element.val(currentVal);
                    }
                }
                prevValue = value;
            }

            function triggerChangeEvent(element) {
                var change;
                if (angular.isFunction(window.Event) && !element.fireEvent) {
                    // modern browsers and Edge
                    try {
                        change = new Event('change', {
                            view: window,
                            bubbles: true,
                            cancelable: false
                        });
                    } catch (ex) {
                        //this is for certain mobile browsers that have the Event object
                        //but don't support the Event constructor
                        change = document.createEvent('HTMLEvents');
                        change.initEvent('change', false, true);
                    } finally {
                        element.dispatchEvent(change);
                    }
                } else if ('createEvent' in document) {
                    // older browsers
                    change = document.createEvent('HTMLEvents');
                    change.initEvent('change', false, true);
                    element.dispatchEvent(change);
                }
                else if (element.fireEvent) {
                    // IE <= 11
                    element.fireEvent('onchange');
                }
            }

            function mouseDownUpHandler(e) {
                if (e.type === 'mousedown') {
                    element.bind('mouseout', mouseoutHandler);
                } else {
                    element.unbind('mouseout', mouseoutHandler);
                }
            }

            element.bind('mousedown mouseup', mouseDownUpHandler);

            function mouseoutHandler() {
                oldSelectionLength = getSelectionLength(this);
                element.unbind('mouseout', mouseoutHandler);
            }

            function keydownHandler(e) {
                var isKeyBackspace = e.which === 8,
                caretPos = getCaretPosition(this) - 1 || 0, //value in keydown is pre change so bump caret position back to simulate post change
                isCtrlZ = e.which === 90 && e.ctrlKey; //ctrl+z pressed

                if (isKeyBackspace) {
                    while(caretPos >= 0) {
                        if (isValidCaretPosition(caretPos)) {
                            //re-adjust the caret position.
                            //Increment to account for the initial decrement to simulate post change caret position
                            setCaretPosition(this, caretPos + 1);
                            break;
                        }
                        caretPos--;
                    }
                    preventBackspace = caretPos === -1;
                }

                if (isCtrlZ) {
                    // prevent IE bug - value should be returned to initial state
                    element.val('');
                    e.preventDefault();
                }
            }

            function eventHandler(e) {
                e = e || {};
                // Allows more efficient minification
                var eventWhich = e.which,
                        eventType = e.type;

                // Prevent shift and ctrl from mucking with old values
                if (eventWhich === 16 || eventWhich === 91) {
                    return;
                }

                var val = element.val(),
                        valOld = oldValue,
                        valMasked,
                        valAltered = false,
                        valUnmasked = unmaskValue(val),
                        valUnmaskedOld = oldValueUnmasked,
                        caretPos = getCaretPosition(this) || 0,
                        caretPosOld = oldCaretPosition || 0,
                        caretPosDelta = caretPos - caretPosOld,
                        caretPosMin = maskCaretMap[0],
                        caretPosMax = maskCaretMap[valUnmasked.length] || maskCaretMap.slice().shift(),
                        selectionLenOld = oldSelectionLength || 0,
                        isSelected = getSelectionLength(this) > 0,
                        wasSelected = selectionLenOld > 0,
                        // Case: Typing a character to overwrite a selection
                        isAddition = (val.length > valOld.length) || (selectionLenOld && val.length > valOld.length - selectionLenOld),
                        // Case: Delete and backspace behave identically on a selection
                        isDeletion = (val.length < valOld.length) || (selectionLenOld && val.length === valOld.length - selectionLenOld),
                        isSelection = (eventWhich >= 37 && eventWhich <= 40) && e.shiftKey, // Arrow key codes

                        isKeyLeftArrow = eventWhich === 37,
                        // Necessary due to "input" event not providing a key code
                        isKeyBackspace = eventWhich === 8 || (eventType !== 'keyup' && isDeletion && (caretPosDelta === -1)),
                        isKeyDelete = eventWhich === 46 || (eventType !== 'keyup' && isDeletion && (caretPosDelta === 0) && !wasSelected),
                        // Handles cases where caret is moved and placed in front of invalid maskCaretMap position. Logic below
                        // ensures that, on click or leftward caret placement, caret is moved leftward until directly right of
                        // non-mask character. Also applied to click since users are (arguably) more likely to backspace
                        // a character when clicking within a filled input.
                        caretBumpBack = (isKeyLeftArrow || isKeyBackspace || eventType === 'click') && caretPos > caretPosMin;

                oldSelectionLength = getSelectionLength(this);

                // These events don't require any action
                if (isSelection || (isSelected && (eventType === 'click' || eventType === 'keyup' || eventType === 'focus'))) {
                    return;
                }

                if (isKeyBackspace && preventBackspace) {
                    element.val(maskPlaceholder);
                    // This shouldn't be needed but for some reason after aggressive backspacing the ctrl $viewValue is incorrect.
                    // This keeps the $viewValue updated and correct.
                    scope.$apply(function () {
                        ctrl.$setViewValue(''); // $setViewValue should be run in angular context, otherwise the changes will be invisible to angular and user code.
                    });
                    setCaretPosition(this, caretPosOld);
                    return;
                }

                // User attempted to delete but raw value was unaffected--correct this grievous offense
                if ((eventType === 'input') && isDeletion && !wasSelected && valUnmasked === valUnmaskedOld) {
                    while (isKeyBackspace && caretPos > caretPosMin && !isValidCaretPosition(caretPos)) {
                        caretPos--;
                    }
                    while (isKeyDelete && caretPos < caretPosMax && maskCaretMap.indexOf(caretPos) === -1) {
                        caretPos++;
                    }
                    var charIndex = maskCaretMap.indexOf(caretPos);
                    // Strip out non-mask character that user would have deleted if mask hadn't been in the way.
                    valUnmasked = valUnmasked.substring(0, charIndex) + valUnmasked.substring(charIndex + 1);

                    // If value has not changed, don't want to call $setViewValue, may be caused by IE raising input event due to placeholder
                    if (valUnmasked !== valUnmaskedOld)
                        valAltered = true;
                }

                // Update values
                valMasked = maskValue(valUnmasked);

                oldValue = valMasked;
                oldValueUnmasked = valUnmasked;

                //additional check to fix the problem where the viewValue is out of sync with the value of the element.
                //better fix for commit 2a83b5fb8312e71d220a497545f999fc82503bd9 (I think)
                if (!valAltered && val.length > valMasked.length)
                    valAltered = true;

                element.val(valMasked);

                //we need this check.  What could happen if you don't have it is that you'll set the model value without the user
                //actually doing anything.  Meaning, things like pristine and touched will be set.
                if (valAltered) {
                    scope.$apply(function () {
                        ctrl.$setViewValue(valMasked); // $setViewValue should be run in angular context, otherwise the changes will be invisible to angular and user code.
                    });
                }

                // Caret Repositioning
                // Ensure that typing always places caret ahead of typed character in cases where the first char of
                // the input is a mask char and the caret is placed at the 0 position.
                if (isAddition && (caretPos <= caretPosMin)) {
                    caretPos = caretPosMin + 1;
                }

                if (caretBumpBack) {
                    caretPos--;
                }

                // Make sure caret is within min and max position limits
                caretPos = caretPos > caretPosMax ? caretPosMax : caretPos < caretPosMin ? caretPosMin : caretPos;

                // Scoot the caret back or forth until it's in a non-mask position and within min/max position limits
                while (!isValidCaretPosition(caretPos) && caretPos > caretPosMin && caretPos < caretPosMax) {
                    caretPos += caretBumpBack ? -1 : 1;
                }

                if ((caretBumpBack && caretPos < caretPosMax) || (isAddition && !isValidCaretPosition(caretPosOld))) {
                    caretPos++;
                }
                oldCaretPosition = caretPos;
                setCaretPosition(this, caretPos);
            }

            function isValidCaretPosition(pos) {
                return maskCaretMap.indexOf(pos) > -1;
            }

            function getCaretPosition(input) {
                if (!input)
                    return 0;
                if (input.selectionStart !== undefined) {
                    return input.selectionStart;
                } else if (document.selection) {
                    if (isFocused(element[0])) {
                        // For IE
                        input.focus();
                        var selection = document.selection.createRange();
                        selection.moveStart('character', input.value ? -input.value.length : 0);
                        return selection.text.length;
                    }
                }
                return 0;
            }

            function setCaretPosition(input, pos) {
                if (!input)
                    return 0;
                if (input.offsetWidth === 0 || input.offsetHeight === 0) {
                    return; // Input's hidden
                }
                if (input.setSelectionRange) {
                    if (isFocused(element[0])) {
                        input.focus();
                        input.setSelectionRange(pos, pos);
                    }
                }
                else if (input.createTextRange) {
                    // For IE
                    var range = input.createTextRange();
                    range.collapse(true);
                    range.moveEnd('character', pos);
                    range.moveStart('character', pos);
                    range.select();
                }
            }

            function getSelectionLength(input) {
                if (!input)
                    return 0;
                if (input.selectionStart !== undefined) {
                    return (input.selectionEnd - input.selectionStart);
                }
                if (window.getSelection) {
                    return (window.getSelection().toString().length);
                }
                if (document.selection) {
                    return (document.selection.createRange().text.length);
                }
                return 0;
            }
        }
    };
}])
.filter('b2bMultiSepartorHighlight', function($sce) {
        return function(text, searchText, searchSeperator) {
            var splitText = function(string) {
                if(angular.isDefined(searchSeperator)){
                    if (string.indexOf(searchSeperator) > -1) {
                        return string.split(searchSeperator);
                    } else {
                        return string
                    }
                }else{
                    return string;
                }
            }
            if (text) {
                var newText = splitText(text);
                var newPhrase = splitText(searchText);
                if (angular.isArray(newPhrase)) {
                    for (var i = 0; i < newText.length; i++) {
                        if (i <= 0) {
                            text = newText[i].replace(new RegExp('(' + newPhrase[i] + ')', 'gi'),
                                '<span class="b2b-search-hightlight">$1</span>');
                        } else {
                            text = text + searchSeperator + ' ' + (newPhrase[i] ? newText[i].replace(new RegExp('(' + newPhrase[i] + ')', 'gi'),
                                '<span class="b2b-search-hightlight">$1</span>') : newText[i]);
                        }
                    }
                } else {
                    text = text.replace(new RegExp('(' + searchText + ')', 'gi'),
                        '<span class="b2b-search-hightlight">$1</span>');
                }
            }
            return $sce.trustAsHtml(text)
        }
    })

    .factory('b2bUserAgent', [function() {
        var mobileRegex = /Android|webOS|iPad|iPhone|iPod|BlackBerry|IEMobile|Opera Mini/i;
        var _isMobile = function() {
            if(/Android/i.test(navigator.userAgent)){
                return /Mobile/i.test(navigator.userAgent);
            }else{
                return mobileRegex.test(navigator.userAgent);
            }

        };
        var _notMobile = function() {
            if(/Android/i.test(navigator.userAgent)){
                return !/Mobile/i.test(navigator.userAgent);
            }else{
                return !mobileRegex.test(navigator.userAgent);
            }

        };
        var _isIE = function() {
            return /msie|trident/i.test(navigator.userAgent);
        };
        var _isFF = function() {
            return /Firefox/.test(navigator.userAgent);
        };
        var _isChrome = function() {
            return /Chrome/.test(navigator.userAgent);
        };
        var _isSafari = function() {
            return /Safari/.test(navigator.userAgent) && !/Chrome/.test(navigator.userAgent);
        };

        return {
            isMobile: _isMobile,
            notMobile: _notMobile,
            isIE: _isIE,
            isFF: _isFF,
            isChrome: _isChrome,
            isSafari: _isSafari
        };
    }])
    .run(['$document', 'b2bUserAgent', function($document, b2bUserAgent) {
        var html = $document.find('html').eq(0);
        if (b2bUserAgent.isIE()) {
            html.addClass('isIE');
        } else {
            html.removeClass('isIE');
        }
    }]);


(function () {
    String.prototype.toSnakeCase = function () {
        return this.replace(/([A-Z])/g, function ($1) {
            return "-" + $1.toLowerCase();
        });
    };
    var concat = function (character, times) {
        character = character || '';
        times = (!isNaN(times) && times) || 0;
        var finalChar = '';
        for (var i = 0; i < times; i++) {
            finalChar += character;
        }
        return finalChar;
    };

    // direction: true for left and false for right
    var pad = function (actualString, width, character, direction) {
        actualString = actualString || '';
        width = (!isNaN(width) && width) || 0;
        character = character || '';
        if (width > actualString.length) {
            if (direction) {
                return concat(character, (width - actualString.length)) + actualString;
            } else {
                return actualString + concat(character, (width - actualString.length));
            }
        }
        return actualString;
    };

    String.prototype.lPad = function (width, character) {
        return pad(this, width, character, true);
    };

    String.prototype.rPad = function (width, character) {
        return pad(this, width, character, false);
    };

    if (!Array.prototype.indexOf) {
        Array.prototype.indexOf = function (val) {
            for (var index = 0; index < this.length; index++) {
                if (this[index] === val) {
                    return index;
                }
            }
            return -1;
        };
    }

    if (!Array.prototype.regexIndexOf) {
        Object.defineProperty(Array.prototype, 'regexIndexOf', {
            enumerable: false,
            value: function (regex, startIndex, loop) {
                startIndex = startIndex && startIndex > -1 ? startIndex : 0;
                for (var index = startIndex; index < this.length; index++) {
                    if (this[index].toString().match(regex)) {
                        return index;
                    }
                }
                if (loop) {
                    for (var index = 0; index < startIndex; index++) {
                        if (this[index].toString().match(regex)) {
                            return index;
                        }
                    }
                }
                return -1;
            }
        })
    }
})();
angular.module("b2bTemplate/audioPlayer/audioPlayer.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/audioPlayer/audioPlayer.html",
    "<div class=\"b2b-audio\">\n" +
    "	<audio preload=\"auto\">\n" +
    "		<source ng-src=\"{{audio.mp3 | trustedAudioUrl}}\" type=\"audio/mp3\"></source>\n" +
    "		<i>Your browser does not support the audio element.</i>\n" +
    "    </audio>\n" +
    "    <div ng-if=\"!audioError\">\n" +
    "	    <div audio-play-pause-icon class=\"controls-wrapper\" ng-click=\"toggleAudio()\" tabindex=\"0\" b2b-accessibility-click=\"13,32\" role=\"button\" aria-label=\"{{isPlayInProgress?audioConfiguration.pauseLabel:audioConfiguration.playLabel}}\">\n" +
    "			<i class=\"icon-controls-pointer\" ng-show='!isPlayInProgress'></i>\n" +
    "			<i class=\"icon-controls-pause\" ng-show='isPlayInProgress'></i>\n" +
    "	    </div>\n" +
    "\n" +
    "	    <div class=\"slider-container-wrapper\">\n" +
    "			<b2b-slider min=\"0\" max=\"{{audio.duration}}\" step=\"1\" skip-interval=\"{{audio.timeShiftInSeconds}}\" hide-disabled-knob ng-disabled=\"disabled\" ng-model=\"audio.currentTime\" on-drag-end=\"setAudioPosition(audio.currentTime)\" on-drag-init=\"isAudioDragging=true\" no-aria-label></b2b-slider>\n" +
    "			<div class=\"timing-container\">\n" +
    "				<span class=\"timing-container-left\">{{timeFormatter(audio.currentTime)}}</span>\n" +
    "				<span class=\"timing-container-right\">{{timeFormatter(audio.duration)}}</span>\n" +
    "				<div class=\"timing-container-spacer\"></div>\n" +
    "			</div>\n" +
    "	    </div>\n" +
    "			\n" +
    "	    <b2b-flyout>\n" +
    "			<div tabindex=\"0\" b2b-accessibility-click=\"13,32\" role=\"button\" aria-label=\"Volume pop-over. Volume set at {{audio.currentVolume}}%.\" class=\"controls-wrapper audio-volume-control\" ng-click=\"volumeControl()\" b2b-flyout-toggler  aria-expanded=\"{{flyoutOpened ? 'true' : 'false'}}\">\n" +
    "				<i class=\"icon-controls-mutespeakers\" ng-show=\"audio.currentVolume === 0\"></i>\n" +
    "				<i class=\"icon-controls-volumedown\" ng-show=\"audio.currentVolume > 0 && audio.currentVolume <= 50\"></i>\n" +
    "				<i class=\"icon-controls-volumeup\" ng-show=\"audio.currentVolume > 50\"></i>\n" +
    "			</div> \n" +
    "			\n" +
    "			<b2b-flyout-content horizontal-placement=\"center\" flyout-style=\"width:70px; height:190px;\" vertical-placement=\"above\">\n" +
    "				<div class=\"b2b-audio-popover text-center\">\n" +
    "					<span>Max</span>\n" +
    "					<b2b-slider min=\"0\" max=\"100\" step=\"1\" skip-interval=\"10\" vertical ng-model=\"audio.currentVolume\" ng-disabled=\"disabled\" class='volume-popover'></b2b-slider>\n" +
    "					<div class=\"min-label\">Min</div>\n" +
    "				</div>\n" +
    "			</b2b-flyout-content>\n" +
    "		</b2b-flyout>\n" +
    "	</div>\n" +
    "	<div ng-if=\"audioError\">\n" +
    "		<a ng-href=\"{{audio.mp3 | trustedAudioUrl}}\" download=\"{{audioFileName}}\">{{audioFileName}}</a>\n" +
    "	</div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/audioRecorder/audioRecorder.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/audioRecorder/audioRecorder.html",
    "<div class=\"b2b-audio-recorder row\">\n" +
    "	<div class=\"b2b-elapsed-time span11\">\n" +
    "		<div ng-if=\"isRecording\">\n" +
    "			<span style=\"padding-right: 25px;\">{{config.whileRecordingMessage}}</span>\n" +
    "			<span>{{timeFormatter(elapsedTime)}}</span>\n" +
    "		</div>\n" +
    "		<span ng-if=\"!isRecording\">{{config.startRecordingMessage}}</span>\n" +
    "	</div>	    \n" +
    "	<div class=\"b2b-controls\" title=\"{{isRecording ? config.stopLabel : config.recordingLabel}}\" aria-label=\"{{isRecording ? config.stopLabel : config.recordingLabel}}\" b2b-accessibility-click=\"13,32\" ng-click=\"toggleRecording()\" tabindex=\"0\" role=\"button\">\n" +
    "	    	<i ng-if=\"isRecording\" class=\"icon-controls-stop\" ></i>\n" +
    "	    	<i ng-if=\"!isRecording\" class=\"icon-controls-record\"></i>\n" +
    "    </div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/backToTop/backToTop.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/backToTop/backToTop.html",
    "<button class=\"btn-arrow b2b-backtotop-button\" type=\"button\" aria-label=\"Back to top\">\n" +
    "    <div class=\"btn-secondary b2b-top-btn\">\n" +
    "        <i class=\"icon-controls-upPRIMARY\" role=\"img\"></i>\n" +
    "    </div>\n" +
    "</button>\n" +
    "");
}]);

angular.module("b2bTemplate/boardstrip/b2bAddBoard.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/boardstrip/b2bAddBoard.html",
    "<div tabindex=\"0\" role=\"menuitem\" b2b-accessibility-click=\"13,32\" ng-click=\"addBoard()\" aria-label=\"Add Board\" class=\"boardstrip-item--add\">\n" +
    "    <div class=\"centered\"><i aria-hidden=\"true\" class=\"icon-controls-add-maximize\"></i> Add board</div>\n" +
    "</div> ");
}]);

angular.module("b2bTemplate/boardstrip/b2bBoard.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/boardstrip/b2bBoard.html",
    "<li b2b-board-navigation tabindex=\"-1\" role=\"menuitem\" aria-label=\"{{boardLabel}} {{getCurrentIndex()===boardIndex?'selected':''}}\" b2b-accessibility-click=\"13,32\" ng-click=\"selectBoard(boardIndex)\" class=\"board-item\" ng-class=\"{'selected': getCurrentIndex()===boardIndex}\">\n" +
    "    <div ng-transclude></div>\n" +
    "    <div class=\"board-caret\" ng-if=\"getCurrentIndex()===boardIndex\">\n" +
    "        <div class=\"board-caret-indicator\"></div>\n" +
    "        <div class=\"board-caret-arrow-up\"></div>\n" +
    "    </div>\n" +
    "</li>");
}]);

angular.module("b2bTemplate/boardstrip/b2bBoardstrip.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/boardstrip/b2bBoardstrip.html",
    "<div class=\"b2b-boardstrip\">\n" +
    "	<div class=\"boardstrip-reel\" role=\"menu\">\n" +
    "		<div class=\"prev-items\">\n" +
    "			<!-- <i tabindex=\"{{isPrevBoard() ? 0 : -1}}\" ng-class=\"{'disabled': !isPrevBoard()}\" role=\"menuitem\" aria-label=\"See previous boards\" b2b-accessibility-click=\"13,32\" ng-click=\"prevBoard()\" class=\"arrow icon-controls-left\"></i> -->\n" +
    "			<button class=\"btn-arrow arrow\" b2b-accessibility-click=\"13,32\" ng-click=\"prevBoard()\" ng-disabled=\"!isPrevBoard()\">\n" +
    "			    <div class=\"btn btn-small btn-alt\"><i class=\"icon-left\"></i>\n" +
    "			    </div>\n" +
    "			    <span class=\"offscreen-text\">Previous boards</span>\n" +
    "			</button>\n" +
    "		</div>\n" +
    "		<div b2b-add-board on-add-board=\"onAddBoard()\"></div>\n" +
    "		<div class=\"board-viewport\"><ul  role=\"menu\" class=\"boardstrip-container\" ng-transclude></ul></div>\n" +
    "		<div class=\"next-items\">\n" +
    "			<!-- <i tabindex=\"{{isNextBoard() ? 0 : -1}}\" ng-class=\"{'disabled': !isNextBoard()}\" role=\"menuitem\" aria-label=\"See next boards\" b2b-accessibility-click=\"13,32\" ng-click=\"nextBoard()\" class=\"arrow icon-controls-right\"></i> -->\n" +
    "			<button class=\"btn-arrow arrow\" b2b-accessibility-click=\"13,32\" ng-click=\"nextBoard()\" ng-disabled=\"!isNextBoard()\">\n" +
    "			    <div class=\"btn btn-small btn-alt\"><i class=\"icon-right\"></i>\n" +
    "			    </div>\n" +
    "			    <span class=\"offscreen-text\">Next boards</span>\n" +
    "			</button>\n" +
    "		</div>\n" +
    "	</div>\n" +
    "</div>\n" +
    "");
}]);

angular.module("b2bTemplate/calendar/datepicker-popup.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/calendar/datepicker-popup.html",
    "<div id=\"datepicker\" class=\"datepicker dropdown-menu\" ng-class=\"{'datepicker-dropdown datepicker-orient-top': !inline, 'datepicker-orient-left': !inline && orientation === 'left', 'datepicker-orient-right': !inline && orientation === 'right'}\" ng-style=\"{position: inline && 'relative', 'z-index': inline && '0', top: !inline && position.top + 'px' || 0, left: !inline && position.left + 'px'}\" style=\"display: block;\" aria-hidden=\"false\" role=\"dialog\" tabindex=\"-1\" b2b-key type=\"table\" columns=\"7\">\n" +
    "    <div class=\"datepicker-days\" style=\"display: block;\">\n" +
    "        <div ng-repeat=\"header in headers\" class=\"text-left\" style=\"width: 100%;\" b2b-append-element=\"header\"></div>\n" +
    "        <table class=\"table-condensed\" role=\"grid\" aria-label=\"Calendar {{title}}\">\n" +
    "            <thead>\n" +
    "                <tr>\n" +
    "                    <th id=\"prev\" class=\"prev\" tabindex=\"0\" b2b-accessibility-click=\"13,32\" aria-label=\"Previous Month\" role=\"button\" b2b-element-focus=\"!disablePrev && getFocus\" ng-style=\"{visibility: visibilityPrev}\" ng-click=\"!disablePrev && move(-1,$event)\"><i class=\"icon-left\" aria-hidden=\"true\"></i></th>\n" +
    "                    <th id=\"month\" tabindex=\"-1\" aria-label=\"{{title}}\" class=\"datepicker-switch\" colspan=\"{{rows[0].length - 2}}\">{{title}}</th>\n" +
    "                    <th id=\"next\" class=\"next\" tabindex=\"0\" b2b-accessibility-click=\"13,32\" b2b-element-focus=\"disablePrev && getFocus\" aria-label=\"Next Month\" role=\"button\" ng-style=\"{visibility: visibilityNext}\" ng-click=\"!disableNext && move(1,$event)\"><i class=\"icon-right\" aria-hidden=\"true\"></i></th>\n" +
    "                </tr>\n" +
    "                <tr ng-show=\"labels.length > 0\">\n" +
    "                    <th id=\"{{label.post}}\" class=\"dow\" ng-repeat=\"label in labels\" aria-hidden=\"true\"><span aria-hidden=\"true\">{{label.pre}}</span></th>\n" +
    "                </tr>\n" +
    "            </thead>\n" +
    "            <tbody>\n" +
    "                <tr ng-repeat=\"row in rows\">\n" +
    "                    <td headers=\"{{dt.header}}\" b2b-key-item=\"dt.focusable\" b2b-accessibility-click=\"13\" b2b-element-focus=\"disablePrev && disableNext && getFocus && (dt.selected || dt.firstFocus || dt.fromDate || dt.dateRange)\" tabindex=\"{{(!(dt.focusable && (dt.selected || dt.firstFocus || dt.fromDate || currFocus)) && -1) || 0}}\" aria-hidden=\"{{(!dt.focusable && true) || false}}\" role=\"{{(dt.focusable && 'gridcell') || 'none'}}\" aria-label=\"{{(dt.focusable && dt.date | date : 'EEEE, MMMM d') || ''}}\" aria-selected=\"{{(dt.focusable && (dt.selected || dt.dateRange) && true) || false}}\" ng-repeat=\"dt in row\" class=\"day magic\" ng-class=\"{'active': dt.focusable && dt.selected && !dt.dateRange, 'start-date': dt.focusable && dt.fromDate && dt.dateRange, 'between-date': dt.focusable && !dt.fromDate && !dt.selected && dt.dateRange, 'end-date': dt.focusable && dt.selected && dt.dateRange, 'old': dt.oldMonth, 'new': dt.nextMonth, 'disabled': dt.disabled, 'due-date': dt.dueDate, 'late-fee': dt.pastDue}\" ng-focus=\"currFocus=true; trapFocus();\" ng-blur=\"currFocus=false; trapFocus();\" title=\"{{(dt.focusable && dt.pastDue && legendMessage) || ''}}\" ng-click=\"!ngDisabled && !dt.disabled && select(dt.date)\">\n" +
    "                        <div aria-hidden=\"true\" tabindex=\"-1\" class=\"show-date\" ng-if=\"!(dt.oldMonth || dt.nextMonth)\">{{dt.label}}</div>{{(dt.focusable && dt.date | date : 'EEEE, MMMM d yyyy') || ''}}{{(dt.focusable && dt.dueDate && '. Bill-due-date.. ') || ''}}{{(dt.focusable && dt.pastDue && legendMessage) || ''}}</td>\n" +
    "                </tr>\n" +
    "            </tbody>\n" +
    "            <tfoot>\n" +
    "                <tr ng-repeat=\"footer in footers\">\n" +
    "                    <th colspan=\"7\" class=\"text-left\" style=\"width: 278px;\" b2b-append-element=\"footer\"></th>\n" +
    "                </tr>\n" +
    "            </tfoot>\n" +
    "        </table>\n" +
    "    </div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/calendar/datepicker.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/calendar/datepicker.html",
    "<div>\n" +
    "    <span class=\"icon-calendar span12\" ng-class=\"{'disabled': ngDisabled}\" ng-transclude></span>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/coachmark/coachmark.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/coachmark/coachmark.html",
    "<div class=\"b2b-coachmark-container\" tabindex=\"-1\" role=\"dialog\" aria-label=\"{{currentCoachmark.contentHeader}} {{currentCoachmark.content}} {{currentCoachmark.offscreenText}}\" ng-style=\"{'top':coackmarkElPos.top,'left':coackmarkElPos.left}\" aria-describedby=\"{{currentCoachmark}}\">\n" +
    "	<i class=\"b2b-coachmark-caret\"></i>\n" +
    "	<div class=\"b2b-coachmark-header\">\n" +
    "		<div class=\"b2b-coachmark-countlabel\"><span ng-if=\"coachmarkIndex !== 0\">{{coachmarkIndex}} of {{(coachmarks.length-1)}}<span></div>\n" +
    "		<div class=\"corner-button\">\n" +
    "			<button type=\"button\" ng-focus=\"closeButtonFocus()\" class=\"close\" title=\"close\" aria-label=\"Close\" ng-click=\"closeCoachmark()\"></button>\n" +
    "		</div>\n" +
    "	</div>\n" +
    "	<div class=\"b2b-coachmark-content\">	\n" +
    "		<i class=\"icon-misc-dimmer\"></i>\n" +
    "		<div class=\"b2b-coachmark-content-header\"><span class=\"offscreen-text\">{{currentCoachmark.offscreenText}}</span>{{currentCoachmark.contentHeader}}</div>\n" +
    "		<div class=\"b2b-coachmark-description\" ng-bind-html=\"currentCoachmark.content | unsafe\"></div>\n" +
    "		<div class=\"b2b-coachmark-btn-group\">\n" +
    "			<a class=\"b2b-coachmark-link\" href=\"javascript:void(0)\" ng-if=\"currentCoachmark.linkLabel !== '' && currentCoachmark.linkLabel !== undefined\" ng-click=\"actionCoachmark(currentCoachmark.linkLabel)\">{{currentCoachmark.linkLabel}}</a>\n" +
    "			<button class=\"btn btn-alt\" ng-if=\"currentCoachmark.buttonLabel !== '' && currentCoachmark.buttonLabel !== undefined\" ng-click=\"actionCoachmark(currentCoachmark.buttonLabel)\">{{currentCoachmark.buttonLabel}}</button>\n" +
    "		</div>	\n" +
    "	</div>	\n" +
    "</div>");
}]);

angular.module("b2bTemplate/dropdowns/b2bDropdownDesktop.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/dropdowns/b2bDropdownDesktop.html",
    "<span b2b-key prev=\"38\" next=\"40\" enable-search contains-search=\"{{containsSearch}}\" ng-class=\"{'large': (dropdownSize === 'large'), 'disabled': (disabled), 'selectWrap': (isInputDropdown), 'selectorModule': (dropdownType === 'menu'), 'linkSelectorModule': (dropdownType === 'link-menu')}\">\n" +
    "    <input b2b-dropdown-toggle b2b-dropdown-validation ng-disabled=\"disabled\" type=\"text\" id=\"{{dropdownId}}\" name=\"{{dropdownName}}\" class=\"awd-select isWrapped\" ng-required=\"dropdownRequired\" ng-model=\"currentSelected.text\" role=\"combobox\" aria-owns=\"listbox{{$id}}\" aria-expanded=\"{{toggleFlag}}\" ng-click=\"toggleDropdown()\" ng-blur=\"setBlur();\" ng-class=\"{'active': toggleFlag, 'closed': !toggleFlag, 'large': (dropdownSize === 'large')}\" style=\"width:100%;\" value=\"{{currentSelected.text}}\" ng-show=\"isInputDropdown\" aria-describedby=\"{{dropdownDescribedBy}}\" readonly=\"readonly\">\n" +
    "    <button type=\"button\" b2b-dropdown-toggle ng-disabled=\"disabled\" class=\"selectModule\" aria-label=\"{{labelText}} {{currentSelected.label}}\" aria-expanded=\"{{toggleFlag}}\" aria-haspopup=\"true\" ng-click=\"toggleDropdown()\" ng-blur=\"setBlur()\" ng-class=\"{'active opened': toggleFlag, 'closed': !toggleFlag, 'large': (dropdownSize === 'large')}\" ng-bind-html=\"currentSelected.text\" ng-show=\"!isInputDropdown\"></button>\n" +
    "    <div ng-class=\"{'selectWrapper': (isInputDropdown), 'moduleWrapper': (!isInputDropdown)}\">\n" +
    "        <ul id=\"listbox{{$id}}\" role=\"{{isInputDropdown?'listbox':'menu'}}\" class=\"b2b-dropdown-desktop-list\" ng-class=\"{'awd-select-list': (isInputDropdown), 'awd-module-list': (!isInputDropdown)}\" tabindex=\"-1\" ng-show=\"toggleFlag\" aria-label=\"Choose options\"></ul>\n" +
    "        <ul class=\"module-optinalcta\" ng-if=\"toggleFlag && optionalCta\" tabindex=\"-1\" aria-hidden=\"false\">\n" +
    "            <li class=\"module-list-item\" tabindex=\"-1\" role=\"menuitem\" value=\"\" aria-label=\"Optinal CTA\" aria-selected=\"true\">\n" +
    "                <span class=\"module-data\" b2b-append-element=\"optionalCta\"></span>\n" +
    "            </li>\n" +
    "        </ul>\n" +
    "</div>\n" +
    "<i class=\"icon-down\" aria-hidden=\"true\"></i>\n" +
    "</span>");
}]);

angular.module("b2bTemplate/dropdowns/b2bDropdownGroupDesktop.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/dropdowns/b2bDropdownGroupDesktop.html",
    "<li b2b-dropdown-group-desktop class=\"optgroup-wrapper\">{{groupHeader}}\n" +
    "    <ul class=\"optgroup\" role=\"group\" aria-label=\"{{groupHeader}}\"></ul>\n" +
    "</li>");
}]);

angular.module("b2bTemplate/dropdowns/b2bDropdownListDesktop.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/dropdowns/b2bDropdownListDesktop.html",
    "<li b2b-dropdown-list-desktop b2b-key-item b2b-accessibility-click=\"13\" aria-selected=\"{{currentSelected.value === dropdownListValue}}\" data-hover=\"{{dropdown.highlightedValue === dropdownListValue}}\" ng-class=\"{'awd-select-list-item': (isInputDropdown), 'module-list-item': (!isInputDropdown)}\" tabindex=\"0\" role=\"{{isInputDropdown?'option':'menuitem'}}\" ng-click=\"selectDropdownItem()\" ng-focus=\"highlightDropdown()\"></li>");
}]);

angular.module("b2bTemplate/fileUpload/fileUpload.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/fileUpload/fileUpload.html",
    "<label class=\"b2b-file-container\">\n" +
    "	<span class=\"b2b-upload-link\" tabindex=\"0\" b2b-accessibility-click=\"13,32\" role=\"button\" ng-transclude></span>\n" +
    "	<input type=\"file\" b2b-file-change tabindex=\"-1\">\n" +
    "</label>");
}]);

angular.module("b2bTemplate/filmstrip/b2bFilmstrip.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/filmstrip/b2bFilmstrip.html",
    "<div class=\"filmstrip span12\" ng-class=\"{'no-header': !filmstripHeading}\">\n" +
    "    <h3 id=\"{{fsId}}_fs_heading\" class=\"header\" ng-if=\"filmstripHeading\" ng-bind=\"filmstripHeading\"></h3>	\n" +
    "    <label id=\"fs_info\" style=\"display: none;\">Use left and right arrow to navigate Press enter to activate</label>\n" +
    "    <label id=\"fs_info_mobile\" style=\"display: none;\">Swipe to navigate Double tap to activate</label>\n" +
    "    <button aria-controls=\"{{fsId}}_fs_list\" aria-label=\"scroll left\" type=\"button\" class=\"btn-circular left-arrow hidden-tablet hidden-phone\" ng-hide=\"filmstripType === 'flex-justify'\" ng-click=\"moveleft()\">\n" +
    "        <div class=\"btn btn-secondary\"><i class=\"icon-left\" aria-hidden=\"true\"></i></div>\n" +
    "    </button>	\n" +
    "    <div class=\"filmstrip-wrapper\">\n" +
    "        <ul class=\"contents\" id=\"{{fsId}}_fs_list\" ng-class=\"{'row flex-justify': filmstripType === 'flex-justify'}\" role=\"listbox\" aria-labelledby=\"{{fsId}}_fs_heading\" aria-describedby=\"{{isMobile ? 'fs_info_mobile' : 'fs_info' }}\" tabindex=\"-1\" ng-transclude>\n" +
    "        </ul>\n" +
    "    </div>\n" +
    "    <button aria-controls=\"{{fsId}}_fs_list\" aria-label=\"scroll right\" type=\"button\" class=\"btn-circular right-arrow hidden-tablet hidden-phone\" ng-hide=\"filmstripType === 'flex-justify'\" ng-click=\"moveright()\">\n" +
    "        <div class=\"btn btn-secondary\"><i class=\"icon-right\" aria-hidden=\"true\"></i></div>\n" +
    "    </button>	\n" +
    "</div>");
}]);

angular.module("b2bTemplate/filmstrip/b2bFilmstripContent.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/filmstrip/b2bFilmstripContent.html",
    "<li id=\"{{cFsId}}_fs_item{{fsIndex}}\" class=\"item\" role=\"option\" ng-transclude aria-selected=\"{{isSelected}}\"></li>\n" +
    "");
}]);

angular.module("b2bTemplate/flyout/flyout.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/flyout/flyout.html",
    "<span class=\"b2b-flyout\"  b2b-flyout-trap-focus-inside>\n" +
    "    <span ng-transclude></span>\n" +
    "</span>");
}]);

angular.module("b2bTemplate/flyout/flyoutContent.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/flyout/flyoutContent.html",
    "<div class=\"b2b-flyout-container\" ng-class=\"{'b2b-flyout-left':horizontalPlacement==='left',\n" +
    "                'b2b-flyout-center':horizontalPlacement==='center', \n" +
    "                'b2b-flyout-right':horizontalPlacement==='right',\n" +
    "                'b2b-flyout-centerLeft':horizontalPlacement==='centerLeft',\n" +
    "                'b2b-flyout-centerRight':horizontalPlacement==='centerRight',  \n" +
    "                'b2b-flyout-above':verticalPlacement==='above', \n" +
    "                'b2b-flyout-below':verticalPlacement==='below',\n" +
    "                'open-flyout': openFlyout,\n" +
    "                'b2b-close-flyout': !openFlyout}\">\n" +
    "    <i class=\"b2b-flyout-caret\" ng-class=\"{'open-flyout': openFlyout, \n" +
    "                                   'b2b-flyout-caret-above':verticalPlacement==='above',\n" +
    "                                   'b2b-flyout-caret-below':verticalPlacement==='below'}\"></i>\n" +
    "    <span ng-transclude></span>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/footer/footer_column_switch_tpl.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/footer/footer_column_switch_tpl.html",
    "<div class=\"footer-columns \" ng-class=\"{'five-column':footerColumns===5, 'four-column':footerColumns===4, 'three-column':footerColumns===3}\" ng-repeat=\"item in footerLinkItems\">\n" +
    "    <h3 class=\"b2b-footer-header\">{{item.categoryName}}</h3>\n" +
    "    <ul>\n" +
    "        <li ng-repeat=\"i in item.values\">\n" +
    "            <a href=\"{{i.href}}\" title=\"{{item.categoryName}} - {{i.displayLink}}\">{{i.displayLink}}</a>  \n" +
    "        </li>\n" +
    "    </ul>\n" +
    "\n" +
    "</div>\n" +
    "\n" +
    "<div ng-transclude></div>\n" +
    "");
}]);

angular.module("b2bTemplate/horizontalTable/horizontalTable.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/horizontalTable/horizontalTable.html",
    "<div class=\"b2b-horizontal-table\">\n" +
    "    <div class=\"b2b-horizontal-table-arrows row span12\">\n" +
    "        <div class=\"span4 b2b-prev-link\">\n" +
    "            <a href=\"javascript:void(0)\" ng-click=\"moveViewportLeft()\" b2b-accessibility-click=\"13,32\" ng-if=\"!disableLeft\">Previous</a>\n" +
    "            <span ng-if=\"disableLeft\" class=\"b2b-disabled-text\">Previous</span>\n" +
    "        </div>\n" +
    "        \n" +
    "        <span class=\"span5 b2b-horizontal-table-column-info\" aria-live=\"polite\" aria-atomic=\"true\" tabindex=\"-1\">\n" +
    "            Showing {{countDisplayText}} {{getColumnSet()[0]+1}}-{{getColumnSet()[1]+1}} of {{numOfCols}} columns\n" +
    "        </span>\n" +
    "\n" +
    "        <div ng-if=\"legendContent\" class=\"span2 b2b-horizontal-table-legend\">\n" +
    "           | <b2b-flyout>\n" +
    "                <div tabindex=\"0\" role=\"button\" aria-haspopup=\"true\" b2b-flyout-toggler b2b-accessibility-click=\"13,32\" aria-expanded=\"{{flyoutOpened ? 'true' : 'false'}}\">\n" +
    "                    Legend\n" +
    "                    <i class=\"icon-controls-down\" role=\"img\"></i>\n" +
    "                </div>\n" +
    "              <b2b-flyout-content horizontal-placement=\"center\" vertical-placement=\"below\">\n" +
    "                <div ng-bind-html=\"legendContent\"></div>\n" +
    "              </b2b-flyout-content>\n" +
    "            </b2b-flyout>\n" +
    "        </div>\n" +
    "        \n" +
    "        <div class=\"span3 text-right b2b-next-link\">\n" +
    "            <a href=\"javascript:void(0)\" ng-click=\"moveViewportRight()\" b2b-accessibility-click=\"13,32\" ng-if=\"!disableRight\">Next</a>\n" +
    "            <span ng-if=\"disableRight\" class=\"b2b-disabled-text\">Next</span>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "    <div class=\"b2b-horizontal-table-inner-container\">\n" +
    "        <span ng-transclude></span>\n" +
    "    </div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/hourPicker/b2bHourpicker.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/hourPicker/b2bHourpicker.html",
    "<div class=\"hp-container\">\n" +
    "    <div class=\"hp-selected\">\n" +
    "        <div b2b-hourpicker-value=\"$index\" days=\"value.days\" start-time=\"value.startTime\" start-meridiem=\"value.startMeridiem\" end-time=\"value.endTime\" end-meridiem=\"value.endMeridiem\" ng-repeat=\"value in finalHourpickerValues\"></div>\n" +
    "    </div>\n" +
    "    <div b2b-hourpicker-panel></div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/hourPicker/b2bHourpickerPanel.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/hourPicker/b2bHourpickerPanel.html",
    "<form name=\"{{'hourpickerForm' + $id}}\">\n" +
    "    <div class=\"hp-checkbox\" role=\"group\">\n" +
    "        <label class=\"checkbox\" for=\"checkbox_{{dayOption.title}}_{{$id}}\" ng-repeat=\"dayOption in hourpicker.dayOptions\">\n" +
    "            <input type=\"checkbox\" id=\"checkbox_{{dayOption.title}}_{{$id}}\" name=\"{{'hourpickerDays' + $id}}\" ng-model=\"hourpickerPanelValue.days[$index].value\" ng-disabled=\"dayOption.disabled\" /><i class=\"skin\"></i><span>{{dayOption.label}}</span>\n" +
    "        </label>\n" +
    "    </div>\n" +
    "    <div class=\"row hp-dropdowns\">\n" +
    "        <div class=\"span4\">\n" +
    "            <label for=\"{{'hourpickerStartTime' + $id}}\">From</label>\n" +
    "            <select id=\"{{'hourpickerStartTime' + $parent.$id}}\" b2b-dropdown type=\"\" ng-model=\"hourpickerPanelValue.startTime\">\n" +
    "                <option b2b-dropdown-list value=\"\">From</option>\n" +
    "                <option b2b-dropdown-list option-repeat=\"startTimeOption in hourpicker.startTimeOptions\" value=\"{{startTimeOption}}\">{{startTimeOption}}</option>\n" +
    "            </select>\n" +
    "        </div>\n" +
    "        <div class=\"span8 radio-buttons\" role=\"radiogroup\" aria-label=\"Select From AM or PM\">\n" +
    "            <label class=\"radio\" for=\"hourpickerStartMeridiem_AM_{{$id}}\">\n" +
    "                <input type=\"radio\" id=\"hourpickerStartMeridiem_AM_{{$id}}\" name=\"{{'hourpickerStartMeridiem' + $id}}\" value=\"am\" ng-model=\"hourpickerPanelValue.startMeridiem\" /><i class=\"skin\"></i><span>AM</span>\n" +
    "            </label>\n" +
    "            <label class=\"radio\" for=\"hourpickerStartMeridiem_PM_{{$id}}\">\n" +
    "                <input type=\"radio\" id=\"hourpickerStartMeridiem_PM_{{$id}}\" name=\"{{'hourpickerStartMeridiem' + $id}}\" value=\"pm\" ng-model=\"hourpickerPanelValue.startMeridiem\" /><i class=\"skin\"></i><span>PM</span>\n" +
    "            </label>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "    <div class=\"row hp-dropdowns\">\n" +
    "        <div class=\"span4\">\n" +
    "            <label for=\"{{'hourpickerEndTime' + $id}}\">To</label>\n" +
    "            <select id=\"{{'hourpickerEndTime' + $parent.$id}}\" b2b-dropdown ng-model=\"hourpickerPanelValue.endTime\">\n" +
    "                <option b2b-dropdown-list value=\"\">To</option>\n" +
    "                <option b2b-dropdown-list option-repeat=\"endTimeOption in hourpicker.endTimeOptions\" value=\"{{endTimeOption}}\">{{endTimeOption}}</option>\n" +
    "            </select>\n" +
    "        </div>\n" +
    "        <div class=\"span8 radio-buttons\" role=\"radiogroup\" aria-label=\"Select To AM or PM\">\n" +
    "            <label class=\"radio\" for=\"hourpickerEndMeridiem_AM_{{$id}}\">\n" +
    "                <input type=\"radio\" id=\"hourpickerEndMeridiem_AM_{{$id}}\" name=\"{{'hourpickerEndMeridiem' + $id}}\" value=\"am\" ng-model=\"hourpickerPanelValue.endMeridiem\" /><i class=\"skin\"></i><span>AM</span>\n" +
    "            </label>\n" +
    "            <label class=\"radio\" for=\"hourpickerEndMeridiem_PM_{{$id}}\">\n" +
    "                <input type=\"radio\" id=\"hourpickerEndMeridiem_PM_{{$id}}\" name=\"{{'hourpickerEndMeridiem' + $id}}\" value=\"pm\" ng-model=\"hourpickerPanelValue.endMeridiem\" /><i class=\"skin\"></i><span>PM</span>\n" +
    "            </label>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "    <div class=\"row hp-buttons\">\n" +
    "        <div class=\"span12\">\n" +
    "            <div style=\"float:right\">\n" +
    "                <button class=\"btn btn-secondary btn-small\" ng-click=\"resetHourpickerPanelValue()\">Clear</button>\n" +
    "                <button class=\"btn btn-alt btn-small\" ng-disabled = \"disableAddBtn\" ng-click=\"updateHourpickerValue()\">{{hourpicker.editMode > -1 ? 'Update' : 'Add'}}</button>\n" +
    "            </div>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "</form>");
}]);

angular.module("b2bTemplate/hourPicker/b2bHourpickerValue.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/hourPicker/b2bHourpickerValue.html",
    "<div class=\"selected-days\">\n" +
    "    <span class=\"day\">{{hourpickerValue.days}} &nbsp; {{hourpickerValue.startTime}} {{hourpickerValue.startMeridiem}} - {{hourpickerValue.endTime}} {{hourpickerValue.endMeridiem}}</span>\n" +
    "    <span style=\"float:right\">\n" +
    "        <i class=\"icon-misc-pen\" role=\"button\" aria-label=\"Edit {{hourpickerValue.days}} {{hourpickerValue.startTime}} {{hourpickerValue.startMeridiem}} - {{hourpickerValue.endTime}} {{hourpickerValue.endMeridiem}}\" title=\"Edit\" tabindex=\"0\" b2b-accessibility-click=\"13,32\" ng-click=\"editHourpickerValue(hourpickerValue.index)\"></i>\n" +
    "        <i class=\"icon-misc-trash\" role=\"button\" aria-label=\"Delete {{hourpickerValue.days}} {{hourpickerValue.startTime}} {{hourpickerValue.startMeridiem}} - {{hourpickerValue.endTime}} {{hourpickerValue.endMeridiem}}\" title=\"Delete\" tabindex=\"0\" b2b-accessibility-click=\"13,32\" ng-click=\"deleteHourpickerValue(hourpickerValue.index)\"></i>\n" +
    "    </span>\n" +
    "    <div style=\"clear:both\"></div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/leftNavigation/leftNavigation.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/leftNavigation/leftNavigation.html",
    "<div class=\"b2b-nav-menu\">\n" +
    "    <div class=\"b2b-subnav-container\">\n" +
    "        <ul class=\"b2b-subnav-content\">\n" +
    "            <li ng-repeat=\"menu in menuData\" ng-click=\"toggleNav($index)\"><a ng-class=\"{'expand': idx==$index}\" aria-label=\"{{menu.name}}\" title=\" \" aria-expanded=\"{{(idx==$index)?true:false;}}\" href=\"javascript:void(0);\">{{menu.name}}<i class=\"b2b-icon-plus-minus\" ng-class=\"idx==$index ? 'icon-expanded' : 'icon-collapsed'\"></i></a>\n" +
    "                <ul ng-class=\"{expand: idx==$index}\">\n" +
    "                    <li ng-repeat=\"menuItem in menu.menuItems\" ng-click=\"liveLink($event, $index, $parent.$index)\"><a aria-hidden=\"{{!(idx==$parent.$index)}}\" aria-label=\"{{menuItem.name}}\" title=\" \" href=\"{{menuItem.href}}\" tabindex=\"{{(idx==$parent.$index)?0:-1;}}\" ng-class=\"{active: itemIdx==$index && navIdx==$parent.$index}\">{{menuItem.name}}</a></li>\n" +
    "                </ul>\n" +
    "            </li>\n" +
    "        </ul>\n" +
    "    </div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/listbox/listbox.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/listbox/listbox.html",
    "<div class=\"b2b-list-box\" tabindex=\"0\" role=\"listbox\" ng-transclude>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/modalsAndAlerts/b2b-backdrop.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/modalsAndAlerts/b2b-backdrop.html",
    "<div class=\"b2b-modal-backdrop fade in hidden-by-modal\" aria-hidden=\"true\" tabindex=\"-1\"></div>");
}]);

angular.module("b2bTemplate/modalsAndAlerts/b2b-window.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/modalsAndAlerts/b2b-window.html",
    "<div class=\"modalwrapper active {{windowClass}}\" ng-class=\"{'modal-landscape': isModalLandscape}\" role=\"{{isNotifDialog?'alertdialog':'dialog'}}\" tabindex=\"-1\" aria-labelledby=\"{{title}}\" aria-describedby=\"{{content}}\">\n" +
    "    <div class=\"modal fade in {{sizeClass}}\" ng-transclude></div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/monthSelector/monthSelector-popup.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/monthSelector/monthSelector-popup.html",
    "<div id=\"monthselector{{$id}}\" class=\"datepicker dropdown-menu monthselector\" \n" +
    "     ng-class=\"{'datepicker-dropdown datepicker-orient-top': !inline, 'datepicker-orient-left': !inline && orientation === 'left', 'datepicker-orient-right': !inline && orientation === 'right'}\" \n" +
    "     ng-style=\"{position: inline && 'relative', 'z-index': inline && '0', top: !inline && position.top + 'px' || 0, left: !inline && position.left + 'px'}\" \n" +
    "     style=\"display: block;\" aria-hidden=\"false\" role=\"dialog presentation\" tabindex=\"-1\">\n" +
    "    <div class=\"datepicker-days\" style=\"display: block;\">\n" +
    "        <span class=\"offscreen-text\" aria-live=\"polite\" aria-atomic=\"true\">{{title}} displaying</span>\n" +
    "        <table class=\"table-condensed\" role=\"grid\">\n" +
    "            <thead>\n" +
    "                <tr ng-repeat=\"header in headers\">\n" +
    "                    <th colspan=\"7\" class=\"text-left\" style=\"width: 278px;\" b2b-append-element=\"header\"></th>\n" +
    "                </tr>\n" +
    "                <tr>\n" +
    "                    <th id=\"prev{{$id}}\" role=\"button\" tabindex=\"0\" b2b-accessibility-click=\"13\" aria-label=\"Previous Year\" ng-class=\"{'prev': !disablePrev}\" ng-style=\"{visibility: visibilityPrev}\" ng-click=\"!disablePrev && move(-1,$event)\"><i class=\"icon-left\"></i></th>\n" +
    "                    <th id=\"year{{$id}}\" role=\"heading\" tabindex=\"-1\" aria-label=\"{{title}}\" class=\"datepicker-switch b2b-monthSelector-label\" colspan=\"{{rows[0].length - 2}}\">{{title}}</th>\n" +
    "                    <th id=\"next{{$id}}\" role=\"button\" tabindex=\"0\" b2b-accessibility-click=\"13\" aria-label=\"Next Year\" ng-class=\"{'next': !disableNext}\" ng-style=\"{visibility: visibilityNext}\" ng-click=\"!disableNext && move(1,$event)\"><i class=\"icon-right\"></i></th>\n" +
    "                </tr>\n" +
    "                <tr ng-show=\"labels.length > 0\">\n" +
    "                    <th id=\"{{label.post}}\" class=\"dow\" ng-repeat=\"label in labels\" aria-hidden=\"true\"><span aria-hidden=\"true\">{{label.pre}}</span></th>\n" +
    "                </tr>\n" +
    "            </thead>\n" +
    "            <tbody b2b-key type=\"table\" columns=\"4\" >\n" +
    "                <tr ng-repeat=\"row in rows\">\n" +
    "                    <td headers=\"{{dt.header}}\" b2b-key-item=\"dt.focusable\" b2b-accessibility-click=\"13,32\" tabindex=\"{{(!(dt.focusable && (dt.selected || dt.firstFocus || currFocus)) && -1) || 0}}\" aria-hidden=\"{{(!dt.focusable && true) || false}}\" role=\"{{(dt.focusable && 'gridcell') || ''}}\" aria-label=\"{{(dt.focusable && dt.date | date : 'MMMM, y') || ''}}\" aria-selected=\"{{(dt.focusable && (dt.selected || dt.dateRange) && true) || false}}\" ng-repeat=\"dt in row\" class=\"day magic\" ng-class=\"{'active': dt.focusable && dt.selected && !dt.dateRange, 'start-date': dt.focusable && dt.fromDate && dt.dateRange, 'between-date': dt.focusable && !dt.fromDate && !dt.selected && dt.dateRange, 'end-date': dt.focusable && dt.selected && dt.dateRange, 'old': dt.oldMonth, 'new': dt.nextMonth, 'disabled': dt.disabled, 'due-date': dt.dueDate, 'late-fee': dt.pastDue}\" ng-focus=\"currFocus=true\" ng-blur=\"currFocus=false\" title=\"{{(dt.focusable && dt.pastDue && legendMessage) || ''}}\" b2b-element-focus=\"inline && dt.focusable && dt.selected && !dt.dateRange\" ng-click=\"!ngDisabled && !dt.disabled && select(dt.date)\">\n" +
    "                        <div aria-hidden=\"true\"  tabindex=\"-1\" class=\"show-date\" ng-if=\"!(dt.oldMonth || dt.nextMonth)\">{{dt.label | limitTo: 3}}</div>\n" +
    "                    </td>\n" +
    "                </tr>\n" +
    "            </tbody>\n" +
    "            <tfoot>\n" +
    "                <tr ng-repeat=\"footer in footers\">\n" +
    "                    <th colspan=\"7\" class=\"text-left\" b2b-append-element=\"footer\"></th>\n" +
    "                </tr>\n" +
    "            </tfoot>\n" +
    "        </table>\n" +
    "    </div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/monthSelector/monthSelector.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/monthSelector/monthSelector.html",
    "<div>\n" +
    "    <span class=\"icon-calendar span12\" ng-class=\"{'disabled': ngDisabled}\" ng-transclude></span>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/monthSelector/monthSelectorLink.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/monthSelector/monthSelectorLink.html",
    "<div>\n" +
    "    <span class=\"span12\" ng-transclude></span>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/pagination/b2b-pagination.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/pagination/b2b-pagination.html",
    "<div class=\"b2b-pager\">\n" +
    "    <div ng-if=\"notMobile && totalPages > 1\">\n" +
    "        <a  tabindex=\"{{currentPage <= 1 ? -1 : 0 }}\" class=\"b2b-pager__item--prev\" b2b-accessibility-click=\"13,32\" title=\"Previous Page\" aria-label=\"Previous Page\" href=\"javascript:void(0);\" ng-click=\"prev($event)\" ng-if=\"totalPages > 10\" ng-class=\"currentPage <= 1 ? 'b2b-pager__item--prev-disabled': '' \">\n" +
    "            <i class=\"icon-left\" aria-hidden=\"true\"></i>\n" +
    "        </a>\n" +
    "        <a droppable=\"{{droppableAttribute}}\" tabindex=\"{{currentPage === 1 ? -1 : 0}}\" ng-class=\"{'b2b-pager__item--noclick': currentPage === 1}\" class=\"b2b-pager__item b2b-pager__item--link\" title=\"1 Page {{checkSelectedPage(page) ? ' is selected' : ''}}\" ng-if=\"totalPages > 10 && currentPage > 6\" b2b-accessibility-click=\"13,32\" ng-click=\"selectPage(1, $event)\" aria-atomic=\"true\" aria-live=\"polite\" aria-label=\"1 Page {{checkSelectedPage(page) ? ' is selected' : ''}}\">\n" +
    "            1<span class=\"offscreen-text\" ng-if=\"currentPage === 1\">Page is selected</span>\n" +
    "        </a>\n" +
    "\n" +
    "        <span class=\"b2b-pager__item\" ng-if=\"totalPages > 10 && currentPage > 6\">...</span>\n" +
    "\n" +
    "        <a droppable=\"{{droppableAttribute}}\" tabindex=\"{{checkSelectedPage(page) ? -1 : 0}}\" href=\"javascript:void(0);\" class=\"b2b-pager__item b2b-pager__item--link\" title=\"{{page}} Page {{checkSelectedPage(page)? ' is selected': ''}}\" b2b-element-focus=\"isFocused(page)\" ng-repeat=\"page in pages\" ng-class=\"{'b2b-pager__item--active': checkSelectedPage(page), 'b2b-pager__item--noclick': checkSelectedPage(page),'b2b-pager__item--droppable': droppableAttribute == true}\" b2b-accessibility-click=\"13,32\" ng-click=\"!checkSelectedPage(page) && selectPage(page, $event)\" aria-atomic=\"true\" aria-live=\"polite\" aria-label=\"{{page}} Page {{checkSelectedPage(page)? ' is selected': ''}}\">\n" +
    "            {{page}}<span class=\"offscreen-text\" ng-if=\"checkSelectedPage(page)\"> is selected</span>\n" +
    "        </a>\n" +
    "\n" +
    "        <span class=\"b2b-pager__item\" ng-if=\"totalPages > 10 && currentPage <= (totalPages - boundary)\">...</span>\n" +
    "\n" +
    "        <a droppable=\"{{droppableAttribute}}\" tabindex=\"{{currentPage === totalPages ? -1 : 0}}\" href=\"javascript:void(0);\" ng-class=\"{'b2b-pager__item--noclick': currentPage === totalPages}\" class=\"b2b-pager__item b2b-pager__item--link\" title=\"{{totalPages}} Page {{checkSelectedPage(page) ? ' is selected' : ''}}\" ng-if=\"totalPages > 10 && currentPage <= (totalPages - boundary)\" b2b-accessibility-click=\"13,32\" ng-click=\"selectPage(totalPages, $event)\" aria-atomic=\"true\" aria-live=\"polite\" aria-label=\"{{totalPages}} Page {{checkSelectedPage(page) ? ' is selected' : ''}}\">\n" +
    "          {{totalPages}}<span class=\"offscreen-text\" ng-if=\"checkSelectedPage(page)\">Page is selected</span>\n" +
    "        </a>\n" +
    "\n" +
    "\n" +
    "        <a tabindex=\"{{currentPage >= totalPages ? -1 : 0 }}\" href=\"javascript:void(0);\" class=\"b2b-pager__item--next\" b2b-accessibility-click=\"13,32\" title=\"Next Page\" aria-label=\"Next Page\" href=\"javascript:void(0);\" ng-click=\"next($event)\" ng-if=\"totalPages > 10\" ng-class=\"currentPage >= totalPages ? 'b2b-pager__item--next-disabled' :'' \">\n" +
    "            <i class=\"icon-right\" aria-hidden=\"true\"></i>\n" +
    "        </a>\n" +
    "        \n" +
    "        <div class=\"fieldLabel b2b-go-to-page\" ng-class=\"{'b2b-go-to-page-inline' : inputClass !== undefined }\" ng-show=\"totalPages > 20\">    \n" +
    "            <label for=\"{{inputId}}\">Go to page:</label>\n" +
    "            <input id=\"{{inputId}}\" class=\"b2b-pager__item--input\" type=\"number\" ng-blur=\"onfocusOut($event)\" ng-focus=\"onfocusIn($event)\" ng-keypress=\"gotoKeyClick($event)\" b2b-only-nums=\"4\" ng-model=\"$parent.gotoPage\" />\n" +
    "            <button class=\"btn-arrow\" ng-click=\"gotoBtnClick()\" ng-disabled=\"$parent.gotoPage !== 0 || $parent.gotoPage !== undefined\" aria-label=\"Go to\">\n" +
    "                <div class=\"btn btn-small btn-secondary\">\n" +
    "                    <i class=\"icon-right\"></i>\n" +
    "                </div>\n" +
    "            </button>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "    <div ng-if=\"isMobile && totalPages > 1\" ng-swipe-right=\"prev()\" ng-swipe-left=\"next()\" ng-class=\"isMobile ? 'b2b-mobile-view': '' \">\n" +
    "        <a droppable=\"{{droppableAttribute}}\" tabindex=\"{{checkSelectedPage(page) ? -1 : 0}}\" href=\"javascript:void(0);\" class=\"b2b-pager__item b2b-pager__item--link\" title=\"Page {{page}}\" b2b-element-focus=\"isFocused(page)\" ng-repeat=\"page in pages\" ng-class=\"{'b2b-pager__item--active': checkSelectedPage(page), fade1: ($index == 0 && currentPage > meanVal+1),  fade2: ($index == 1 && currentPage > meanVal+1), fadesl: ($index == pages.length-2 && currentPage < totalPages - meanVal),  fadel: ($last && currentPage < totalPages - meanVal), 'b2b-pager__item--noclick': checkSelectedPage(page)}\" b2b-accessibility-click=\"13,32\" ng-click=\"selectPage(page, $event)\">{{page}}</a>\n" +
    "    </div>\n" +
    "</div>\n" +
    "");
}]);

angular.module("b2bTemplate/paneSelector/paneSelector.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/paneSelector/paneSelector.html",
    "<div class=\"panes\" ng-transclude></div>");
}]);

angular.module("b2bTemplate/paneSelector/paneSelectorPane.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/paneSelector/paneSelectorPane.html",
    "<div class=\"pane-block\" ng-transclude></div>");
}]);

angular.module("b2bTemplate/profileCard/profileCard-addUser.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/profileCard/profileCard-addUser.html",
    "<div  class=\"span3 b2b-profile-card b2b-add-user\">\n" +
    "    <div class=\"atcenter\">\n" +
    "        <div class=\"circle\"><i class=\"icon-add-maximize\"></i></div>\n" +
    "        <div>Create new user</div>\n" +
    "    </div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/profileCard/profileCard.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/profileCard/profileCard.html",
    "<div class=\"span3 b2b-profile-card\">\n" +
    "    <div class=\"top-block\">\n" +
    "       <div class=\"profile-image\">\n" +
    "            <img ng-if=\"image\" profile-name=\"{{profile.name}}\" ng-src=\"{{profile.img}}\" alt=\"{{profile.name}}\">\n" +
    "            <span ng-if=\"!image\" class=\"default-img\">{{initials}}</span>\n" +
    "\n" +
    "            <h4 class=\"name\">{{profile.name}}</h4>\n" +
    "\n" +
    "            <p class=\"status\">\n" +
    "                <span class=\"status-icon\" ng-class=\"{'status-green':colorIcon==='green','status-red':colorIcon==='red','status-blue':colorIcon==='blue','status-yellow':colorIcon==='yellow'}\">   \n" +
    "                </span>\n" +
    "                <span>{{profile.state}}<span ng-if=\"badge\" class=\"status-badge\">Admin</span></span>\n" +
    "            </p>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "    <div class=\"bottom-block\">\n" +
    "         <div class=\"profile-details\">\n" +
    "            <label>Username</label>\n" +
    "            <div ng-if=\"shouldClip(profile.userName)\" ng-mouseover=\"showUserNameTooltip = true;\" ng-mouseleave=\"showUserNameTooltip = false;\">\n" +
    "                <div ng-if=\"shouldClip(profile.userName)\" class=\"tooltip\" b2b-tooltip show-tooltip=\"showUserNameTooltip\">\n" +
    "                    {{profile.userName.slice(0, 25)+'...'}}\n" +
    "                    <div class=\"arrow\"></div>\n" +
    "                    <div class=\"tooltip-wrapper\" role=\"tooltip\" aria-live=\"polite\" aria-atomic=\"false\" style=\"z-index:1111\">\n" +
    "                        <div class=\"tooltip-size-control\">\n" +
    "                            <div class=\"helpertext\" tabindex=\"-1\" role=\"tooltip\">\n" +
    "                                {{profile.userName}}\n" +
    "                            </div>\n" +
    "                        </div>\n" +
    "                    </div>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "            <div ng-if=\"!shouldClip(profile.userName)\">\n" +
    "                {{profile.userName}}\n" +
    "            </div>\n" +
    "            <label>Email</label>\n" +
    "            <div ng-if=\"shouldClip(profile.email)\" ng-mouseover=\"showEmailTooltip = true;\" ng-mouseleave=\"showEmailTooltip = false;\">\n" +
    "                <div ng-if=\"shouldClip(profile.email)\" class=\"tooltip\" data-placement=\"bottom\" b2b-tooltip show-tooltip=\"showEmailTooltip\">\n" +
    "                    {{profile.email.slice(0, 25)+'...'}}\n" +
    "                    <div class=\"arrow\"></div>\n" +
    "                    <div class=\"tooltip-wrapper\" role=\"tooltip\" aria-live=\"polite\" aria-atomic=\"false\" style=\"z-index:1111\">\n" +
    "                        <div class=\"tooltip-size-control\">\n" +
    "                            <div class=\"helpertext\" tabindex=\"-1\" role=\"tooltip\">\n" +
    "                                {{profile.email}}\n" +
    "                            </div>\n" +
    "                        </div>\n" +
    "                    </div>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "            <div ng-if=\"!shouldClip(profile.email)\">\n" +
    "                {{profile.email}}\n" +
    "            </div>\n" +
    "            <label>Role</label>\n" +
    "            <div ng-if=\"shouldClip(profile.role)\" ng-mouseover=\"showRoleTooltip = true;\" ng-mouseleave=\"showRoleTooltip = false;\">\n" +
    "                <div ng-if=\"shouldClip(profile.role)\" class=\"tooltip\" b2b-tooltip show-tooltip=\"showRoleTooltip\">\n" +
    "                    {{profile.role.slice(0, 25)+'...'}}\n" +
    "                    <div class=\"tooltip-wrapper\" role=\"tooltip\" aria-live=\"polite\" aria-atomic=\"false\" style=\"z-index:1111\">\n" +
    "                        <div class=\"tooltip-size-control\">\n" +
    "                            <div class=\"helpertext\" tabindex=\"-1\" role=\"tooltip\">\n" +
    "                                {{profile.role}}\n" +
    "                            </div>\n" +
    "                        </div>\n" +
    "                    </div>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "            <div ng-if=\"!shouldClip(profile.role)\">\n" +
    "                {{profile.role}}\n" +
    "            </div>\n" +
    "            <label>Last login</label>\n" +
    "            <div ng-if=\"shouldClip(profile.lastLogin)\" ng-mouseover=\"showLastLoginTooltip = true;\" ng-mouseleave=\"showLastLoginTooltip = false;\">\n" +
    "                <div ng-if=\"shouldClip(profile.lastLogin)\" class=\"tooltip\" b2b-tooltip show-tooltip=\"showLastLoginTooltip\">\n" +
    "                    {{profile.lastLogin.slice(0, 25)+'...'}}\n" +
    "                    <div class=\"tooltip-wrapper\" role=\"tooltip\" aria-live=\"polite\" aria-atomic=\"false\" style=\"z-index:1111\">\n" +
    "                        <div class=\"tooltip-size-control\">\n" +
    "                            <div class=\"helpertext\" tabindex=\"-1\" role=\"tooltip\">\n" +
    "                                {{profile.lastLogin}}\n" +
    "                            </div>\n" +
    "                        </div>\n" +
    "                    </div>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "            <div ng-if=\"!shouldClip(profile.lastLogin)\">\n" +
    "                {{profile.lastLogin}}\n" +
    "            </div>\n" +
    "         </div>\n" +
    "    </div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/reorderList/reorderList.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/reorderList/reorderList.html",
    "<div>\n" +
    "	<div class=\"b2b-reorder-list\" tabindex=\"0\" role=\"listbox\" ng-transclude>\n" +
    "	</div>\n" +
    "	<div  class=\"b2b-reorder-list-btngroup\">\n" +
    "		<button class=\"btn btn-small\" ng-class=\"{'btn-alt':moveUp,'btn-specialty':!moveUp}\" ng-disabled=\"!moveUp\" aria-disabled=\"{{!moveUp}}\" ng-click=\"shiftTo('top')\" b2b-accessibility-click=\"13,32\" type=\"button\"><i aria-hidden=\"true\" class=\"icon-arrows-vertical-arrow white\"></i>Move to top</button>		\n" +
    "		<button class=\"btn btn-small\" ng-class=\"{'btn-alt':moveUp,'btn-specialty':!moveUp}\" ng-disabled=\"!moveUp\" aria-disabled=\"{{!moveUp}}\" ng-click=\"shiftTo('up')\" b2b-accessibility-click=\"13,32\" type=\"button\"><i aria-hidden=\"true\" class=\"icon-arrows-vertical-arrow white\"></i>Move up</button>	\n" +
    "		<button id=\"moveDown\" class=\"btn btn-small\" ng-class=\"{'btn-alt':moveDown,'btn-specialty':!moveDown}\" ng-disabled=\"!moveDown\" aria-disabled=\"{{!moveDown}}\" ng-click=\"shiftTo('down')\" b2b-accessibility-click=\"13,32\" type=\"button\"><i aria-hidden=\"true\" class=\"icon-arrows-vertical-arrow-down white\"></i>Move down</button>		\n" +
    "		<button  class=\"btn btn-small\" ng-class=\"{'btn-alt':moveDown,'btn-specialty':!moveDown}\" ng-disabled=\"!moveDown\" aria-disabled=\"{{!moveDown}}\" ng-click=\"shiftTo('bottom')\" b2b-accessibility-click=\"13,32\" type=\"button\"><i aria-hidden=\"true\" class=\"icon-arrows-vertical-arrow-down white\"></i>Move to bottom</button>	\n" +
    "		<a id=\"resetLink\" b2b-accessibility-click=\"13,32\" title=\"Original column order\" href=\"javascript:void(0);\" ng-click=\"reset()\">Original column order</a>\n" +
    "	</div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/searchField/searchField.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/searchField/searchField.html",
    "<div class=\"search-bar\">\n" +
    "    <div class='input-container' ng-blur=\"blurInput()\">\n" +
    "        <input type=\"text\" class=\"innershadow b2b-search-input-field\" id=\"{{configObj.labelId}}\"  b2b-search-input ng-model=\"inputModel\" ng-disabled=\"disabled\" b2b-reset ng-keydown=\"selectionIndex($event)\" placeholder=\"{{configObj.ghostText}}\" style=\"width:100%\" maxlength=\"{{configObj.maxLength}}\" title=\"{{inputModel}}\" aria-autocomplete=\"true\" aria-haspopup=\"true\" aria-expanded=\"{{showListFlag}}\" aria-activedescendant=\"\"/>\n" +
    "            <button class=\"btn-search\" ng-disabled=\"disabled\" ng-click=\"startSearch()\" b2b-accessibility-click=\"13,32\" aria-label=\"Search\" ng-focus=\"showListFlag = false\" type=\"button\"><i class=\"icon-controls-magnifyingglass\"></i></button>\n" +
    "    </div>\n" +
    "    <div class=\"search-suggestion-wrapper\" ng-if=\"filterList.length >=0\" ng-show=\"showListFlag\">\n" +
    "        <ul class=\"search-suggestion-list\" role=\"listbox\">\n" +
    "            <li class=\"no-result\" ng-if=\"filterList.length == 0 && configObj.display\">{{configObj.resultText}}</li>\n" +
    "            <li class=\"search-suggestion-item\" ng-if=\"filterList.length\" ng-repeat=\"item in filterList | limitTo:configObj.noOfItemsDisplay track by $index\" ng-bind-html=\"item.title | b2bMultiSepartorHighlight:inputModel:configObj.searchSeperator\" ng-class=\"{active:isActive($index,filterList.length)}\" ng-click=\"selectItem($index,item.title)\">\n" +
    "                {{item.title}}     \n" +
    "            </li>\n" +
    "        </ul>\n" +
    "    </div>\n" +
    "    <span aria-live=\"polite\" aria-atomic=\"true\" class=\"offscreen-text\">{{inputModel.length>0?inputModel:configObj.ghostText}}</span>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/seekBar/seekBar.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/seekBar/seekBar.html",
    "<div class=\"b2b-seek-bar-container\" ng-class=\"{vertical:verticalSeekBar}\">\n" +
    "    <div class=\"b2b-seek-bar-track-container\">\n" +
    "        <div class=\"b2b-seek-bar-track\"></div>\n" +
    "        <div class=\"b2b-seek-bar-track-fill\"></div>\n" +
    "    </div>\n" +
    "    <div class=\"b2b-seek-bar-knob-container\" role=\"menu\"  >\n" +
    "        <div class=\"b2b-seek-bar-knob\" tabindex=\"0\" role=\"menuitem\"></div>\n" +
    "    </div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/slider/slider.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/slider/slider.html",
    "<div class=\"b2b-slider-container\" ng-class=\"{'vertical':verticalSlider}\">\n" +
    "    <div class=\"slider-track-container\">\n" +
    "        <div class=\"slider-track\">\n" +
    "        	<div ng-repeat=\"snapPoint in sliderSnapPoints\" ng-style=\"calculateLeft(snapPoint)\" class=\"slider-snap-point\" role=\"presentation\"></div>\n" +
    "        </div>\n" +
    "        <div class=\"slider-track-fill\" ng-style=\"{backgroundColor:trackFillColor}\" ng-class=\"{'trackfill-disabled-color':isSliderDisabled}\"></div>\n" +
    "    </div>\n" +
    "    <div class=\"slider-knob-container\" ng-class=\"{'slider-knob-hidden':hideKnob}\">\n" +
    "\n" +
    "    	<span class=\"tooltiptext\" ng-class=\"{'disabled-tooltip':isSliderDisabled}\">{{currentModelValue}}</span>\n" +
    " \n" +
    "        <div class=\"slider-knob\" role=\"slider\" aria-valuemin=\"{{min}}\" aria-valuemax=\"{{max}}\" aria-labelledby=\"{{labelId}}\" aria-valuenow=\"{{currentModelValue}}\" aria-valuetext=\"{{currentModelValue}}{{postAriaLabel}}\" aria-orientation=\"{{verticalSlider ? 'vertical' : 'horizontal'}}\"></div>\n" +
    "\n" +
    "    </div>\n" +
    "</div>  ");
}]);

angular.module("b2bTemplate/spinButton/spinButton.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/spinButton/spinButton.html",
    "<div class=\"btn-group btn-spinbutton-toggle\" ng-class=\"{'disabled': disabledFlag}\">\n" +
    "    <button type=\"button\" tabindex=\"{{isMobile?'0':'-1'}}\" aria-hidden=\"{{notMobile}}\" class=\"btn btn-secondary btn-prev icon-subtractminimize\" ng-click=\"minus();focusInputSpinButton($event)\" aria-label=\"Remove {{step}}\" aria-controls=\"{{spinButtonId}}\" ng-class=\"{'disabled': isMinusDisabled()}\" ng-disabled=\"isMinusDisabled()\" aria-disabled=\"{{isMinusDisabled()}}\" role=\"spinbutton\"></button>\n" +
    "    <input class=\"btn pull-left\" id=\"{{spinButtonId}}\" type=\"tel\" b2b-only-nums=\"3\" maxlength=\"3\" min={{min}} max={{max}} data-max-value=\"{{max}}\" ng-model=\"inputValue[inputModelKey]\" value=\"{{inputValue[inputModelKey]}}\" aria-live=\"polite\" aria-valuenow=\"{{inputValue[inputModelKey]}}\" aria-valuemin=\"{{min}}\" aria-valuemax=\"{{max}}\" ng-disabled=\"disabledFlag\">\n" +
    "    <button type=\"button\" tabindex=\"{{isMobile?'0':'-1'}}\" aria-hidden=\"{{notMobile}}\" class=\"btn btn-secondary btn-next icon-add-maximize\" ng-click=\"plus();focusInputSpinButton($event)\" aria-label=\"Add {{step}}\" aria-controls=\"{{spinButtonId}}\" ng-class=\"{'disabled': isPlusDisabled()}\" ng-disabled=\"isPlusDisabled()\" aria-disabled=\"{{isPlusDisabled()}}\" role=\"spinbutton\"></button>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/statusTracker/statusTracker.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/statusTracker/statusTracker.html",
    "<div class=\"b2b-status-tracker row\">\n" +
    "	<button tabindex=\"0\" ng-disabled=\"currentViewIndex === 0\" ng-if=\"statuses.length > b2bStatusTrackerConfig.maxViewItems\" class=\"btn-arrow\" type=\"button\" aria-label=\"Previous status\" ng-click=\"previousStatus()\">\n" +
    "	    <div class=\"btn btn-small btn-secondary\">\n" +
    "	        <i class=\"icon-left\"></i>\n" +
    "	    </div>\n" +
    "	</button>\n" +
    "	<div ng-repeat=\"status in statuses\" class=\"b2b-status-tracker-step {{ status.state }}\" ng-show=\"isInViewport($index)\">\n" +
    "	    <p class=\"b2b-status-tracker-heading\">{{status.heading}}</p>\n" +
    "	    <div class=\"progress\">\n" +
    "	        <div class=\"progress-bar\">\n" +
    "	        	<span class=\"hidden-spoken\">\n" +
    "	            	{{ removeCamelCase(status.state) }}\n" +
    "	        	</span>\n" +
    "	        </div>\n" +
    "	    </div>\n" +
    "	    <div class=\"b2b-status-tracker-estimate {{status.state}}\">\n" +
    "	    	<i ng-show=\"status.estimate !== '' && status.estimate !== undefined\" ng-class=\"b2bStatusTrackerConfig.icons[status.state]\"></i>\n" +
    "	    	&nbsp;\n" +
    "	    	<span ng-bind-html=\"status.estimate\"></span>\n" +
    "	    </div>\n" +
    "	    \n" +
    "	    <div class=\"b2b-status-tracker-description\" ng-bind-html=\"status.description\"> \n" +
    "	    </div>\n" +
    "	</div>\n" +
    "	<button tabindex=\"0\" ng-disabled=\"currentViewIndex + b2bStatusTrackerConfig.maxViewItems === statuses.length\" ng-if=\"statuses.length > b2bStatusTrackerConfig.maxViewItems\" class=\"btn-arrow\" type=\"button\" aria-label=\"Next status\" ng-click=\"nextStatus()\">\n" +
    "	    <div class=\"btn btn-small btn-secondary\">\n" +
    "	        <i class=\"icon-right\"></i>\n" +
    "	    </div>\n" +
    "	</button>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/stepTracker/stepTracker.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/stepTracker/stepTracker.html",
    "<div class=\"b2b-step-tracker\">\n" +
    "    <button class=\"btn-arrow b2b-left-arrow\" ng-click=\"previousStatus()\" ng-disabled=\"currentViewIndex === 0\" ng-if=\"stepsItemsObject.length > b2bStepTrackerConfig.maxViewItems\" aria-label=\"Previous step\">\n" +
    "		<div class=\"btn btn-left btn-small btn-secondary\"><i class=\"icon-left\"></i></div>\n" +
    "	</button>\n" +
    "    <button class=\"btn-arrow b2b-right-arrow\" ng-click=\"nextStatus()\" ng-disabled=\"currentViewIndex + b2bStepTrackerConfig.maxViewItems === stepsItemsObject.length\" ng-if=\"stepsItemsObject.length > b2bStepTrackerConfig.maxViewItems\" aria-label=\"Next step\">\n" +
    "		<div class=\"btn btn-small btn-right btn-secondary\"><i class=\"icon-right\"></i></div>\n" +
    "	</button>\n" +
    "    <ul class=\"b2b-steps\">\n" +
    "        <li ng-class=\"{'b2b-step-done':$index < currentIndex - 1 ,'b2b-step-on':$index == currentIndex - 1}\" \n" +
    "            ng-repeat=\"stepsItem in stepsItemsObject\" ng-show=\"isInViewport($index)\">\n" +
    "			<p class=\"b2b-step-text\" data-sm-text=\"{{stepsItem.dataMobile}}\" data-large-text=\"{{stepsItem.dataDesktop}}\">{{stepsItem.text}}</p>\n" +
    "            <span class=\"hidden-spoken\">\n" +
    "                {{($index < currentIndex - 1)? 'Complete. '+stepsItem.text+' '+stepsItem.dataMobile:''}} \n" +
    "                {{($index == currentIndex - 1)? 'In Progress. '+stepsItem.text+' '+stepsItem.dataMobile:''}} \n" +
    "                {{($index > currentIndex - 1)? 'Incomplete. '+stepsItem.text+' '+stepsItem.dataMobile:''}}\n" +
    "            </span>\n" +
    "        </li>\n" +
    "    </ul>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/switches/switches-spanish.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/switches/switches-spanish.html",
    "<div class=\"switch-overlay\" aria-hidden=\"true\">\n" +
    "    <span class=\"btn-slider-on\"><span class=\"hidden-spoken\">seleccione para hacer activo</span><i class=\"activo\"></i></span>\n" +
    "    <span class=\"switch-handle\"></span>\n" +
    "    <span class=\"btn-slider-off\"><span class=\"hidden-spoken\">seleccione para hacer inactivo</span><i class=\"inactivo\"></i></span>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/switches/switches-v2.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/switches/switches-v2.html",
    "<div class=\"btn-switch\">\n" +
    "	<fieldset role=\"radiogroup\" b2b-radio-group-accessibility aria-labelledby=\"{{switchId}}--grouplabel\" ng-disabled=\"disabledFlag\">\n" +
    "		<div>\n" +
    "			<input type=\"radio\" name=\"{{switchId}}--name\" ng-model=\"ngModelSwitch\" id=\"{{switchId}}-1\" value=\"true\" class=\"radio\" ng-disabled=\"disabledFlag\">\n" +
    "			<label class=\"offscreen-text\" for=\"{{switchId}}-1\">{{ourLabel}} {{options.on}}</label>\n" +
    "		</div>\n" +
    "		<div>\n" +
    "			<input type=\"radio\" name=\"{{switchId}}--name\" ng-model=\"ngModelSwitch\" id=\"{{switchId}}-2\"  value=\"false\" class=\"radio\" ng-disabled=\"disabledFlag\" >\n" +
    "			<label class=\"offscreen-text\" for=\"{{switchId}}-2\">{{ourLabel}} {{options.off}}</label>\n" +
    "		</div>\n" +
    "	</fieldset>\n" +
    "	<div class=\"switch-overlay\" aria-hidden=\"true\" role=\"button\">\n" +
    "		<span class=\"btn-slider-on\">{{options.on}}</span>\n" +
    "		<span class=\"switch-handle\"></span>\n" +
    "		<span class=\"btn-slider-off\">{{options.off}}</span>\n" +
    "	</div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/switches/switches.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/switches/switches.html",
    "<div class=\"switch-overlay\" aria-hidden=\"true\">\n" +
    "    <span class=\"btn-slider-on\">{{options.on}}</span>\n" +
    "    <span class=\"switch-handle\"></span>\n" +
    "    <span class=\"btn-slider-off\">{{options.off}}</span>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/tableMessages/tableMessage.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/tableMessages/tableMessage.html",
    "<div class=\"b2b-table-message\">\n" +
    "    <div class=\"b2b-message\" ng-if=\"msgType === 'noMatchingResults'\">\n" +
    "        <div class=\"b2b-magnify-glass\"></div>\n" +
    "        <div>\n" +
    "            <div ng-transclude></div>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "    <div class=\"b2b-message\" ng-if=\"msgType == 'infoCouldNotLoad'\">\n" +
    "        <div class=\"icon-alert b2b-alert\" aria-label=\"Oops! The information could not load at this time. Please click link to refresh the page.\"></div>\n" +
    "        <div>Oops!</div>\n" +
    "        <div>The information could not load at this time.</div>\n" +
    "        <div>Please <a href=\"javascript:void(0)\" title=\"Refresh the page\" ng-click=\"refreshAction($event)\">refresh the page</a>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "    <div class=\"b2b-message\" ng-if=\"msgType == 'magnifySearch'\">\n" +
    "        <div class=\"b2b-magnify-glass\"></div>\n" +
    "        <div>\n" +
    "            <p class=\"b2b-message-title\">Please input values to\n" +
    "                <br/> begin your search.</p>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "    <div class=\"b2b-message\" ng-if=\"msgType === 'loadingTable'\">\n" +
    "        <div class=\"icon-spinner-ddh b2b-loading-dots\" aria-label=\"The data is currently loading.\"></div>\n" +
    "        <div ng-transclude></div>\n" +
    "    </div>\n" +
    "</div>\n" +
    "");
}]);

angular.module("b2bTemplate/tableScrollbar/tableScrollbar.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/tableScrollbar/tableScrollbar.html",
    "<div class=\"b2b-table-scrollbar\">\n" +
    "    <div class=\"b2b-scrollbar-arrows\">\n" +
    "        <button class=\"btn-arrow  b2b-scrollbar-arrow-left\" type=\"button\" ng-attr-aria-label=\"{{disableLeft ? 'Scroll Left Disabled' : 'Scroll Left'}}\" ng-click=\"scrollLeft()\" ng-disabled=\"disableLeft\"><div class=\"btn btn-alt\"><i class=\"icon-left\"></i></div></button>\n" +
    "        <button class=\"btn-arrow b2b-scrollbar-arrow-right\" ng-attr-aria-label=\"{{disableRight ? 'Scroll Right Disabled' : 'Scroll Right'}}\" ng-click=\"scrollRight()\" ng-disabled=\"disableRight\" type=\"button\"><div class=\"btn btn-alt\"><i class=\"icon-right\"></i></div></button>\n" +
    "    </div>\n" +
    "    <div class=\"b2b-table-inner-container\">\n" +
    "        <span ng-transclude></span>\n" +
    "    </div>\n" +
    "</div>");
}]);

angular.module("b2bTemplate/tables/b2bResponsiveRow.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/tables/b2bResponsiveRow.html",
    "<tr class=\"data-row-list b2b-expand-row\" toggle-flag ng-if=\"expandFlag\"  >\n" +
    "    <td colspan=\"{{headerValues.length}}\" headers=\"{{headerValues[0].headerId}} {{firstTdId}}\">\n" +
    "        <div b2b-collapse=\"!(expandFlag)\">\n" +
    "            <ul class=\"bullet\">\n" +
    "                <li b2b-responsive-list=\"{{$index}}\" ng-show=\"isVisible()\" ng-repeat=\"headerValue in headerValues\">\n" +
    "                    <div ng-class=\"headerClass[$index]\" class=\"b2b-complex-heading\">{{headerValue.headerName}}</div>\n" +
    "                    <div ng-class=\"bodyClass[$index]\">{{rowValues[$index]}}</div>\n" +
    "                </li>\n" +
    "            </ul>\n" +
    "        </div>\n" +
    "    </td>\n" +
    "</tr>");
}]);

angular.module("b2bTemplate/tables/b2bTable.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/tables/b2bTable.html",
    "<table ng-class=\"{'complex-table': responsive && active}\" ng-transclude></table>");
}]);

angular.module("b2bTemplate/tables/b2bTableBody.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/tables/b2bTableBody.html",
    "<td ng-hide=\"isHidden()\" ng-transclude></td>");
}]);

angular.module("b2bTemplate/tables/b2bTableHeaderSortable.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/tables/b2bTableHeaderSortable.html",
    "<th scope=\"col\" role=\"columnheader\" aria-sort=\"{{sortPattern !== 'null' && 'none' || sortPattern}}\" aria-label=\"{{headerName}} {{sortable !== 'false' && ': activate to sort' || ' '}} {{sortPattern !== 'null' && '' || sortPattern}}\" tabindex=\"{{sortable !== 'false' && '0' || '-1'}}\" b2b-accessibility-click=\"13,32\" ng-click=\"(sortable !== 'false') && sort();\" ng-hide=\"isHidden()\">\n" +
    "    <span ng-transclude></span>\n" +
    "    <i ng-class=\"{'icon-arrows-sort-arrow active': sortPattern === 'ascending', 'icon-arrows-sort-arrow active down': sortPattern === 'descending'}\"></i>\n" +
    "</th>");
}]);

angular.module("b2bTemplate/tables/b2bTableHeaderUnsortable.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/tables/b2bTableHeaderUnsortable.html",
    "<th scope=\"col\" ng-hide=\"isHidden()\" ng-transclude></th>");
}]);

angular.module("b2bTemplate/tabs/b2bTab.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/tabs/b2bTab.html",
    "<li class=\"tab\" \n" +
    "    ng-class=\"{'active': isTabActive()}\" ng-click=\"clickTab()\" ng-hide=\"tabItem.disabled\">\n" +
    "    <a href=\"javascript:void(0)\"  tabindex=\"{{(isTabActive() && tabItem.disabled)?-1:0}}\"\n" +
    "       ng-disabled=\"tabItem.disabled\" title=\"{{tabItem.title}}\" aria-expanded=\"{{(isTabActive() && !tabItem.disabled)}}\" \n" +
    "       b2b-accessibility-click=\"13,32\">\n" +
    "       	<span ng-transclude></span>\n" +
    "       	<span class=\"hidden-spoken\" ng-if=\"isTabActive()\">Active</span>\n" +
    "       </a>\n" +
    "    \n" +
    "</li>");
}]);

angular.module("b2bTemplate/tabs/b2bTabset.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/tabs/b2bTabset.html",
    "<ul class=\"tabs promo-tabs\" ng-transclude></ul>");
}]);

angular.module("b2bTemplate/treeNav/groupedTree.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/treeNav/groupedTree.html",
    "<ul role=\"group\">\n" +
    "    <li aria-expanded=\"{{(member.active?true:false)}}\" role=\"treeitem\" aria-label=\"{{key}}\" ng-repeat='(key,value) in collection | groupBy : \"grpChild\"' b2b-tree-link><a class=\"grp\" ng-class=\"{'active': value.showGroup == true}\" tabindex=\"-1\" href='javascript:void(0);'>{{(key)?key:''}}<span class=\"b2b-tree-node-icon\"><i ng-class=\"{'icon-expanded': value.showGroup == true, 'icon-collapsed': value.showGroup == undefined || value.showGroup == false }\"></i></span></a>\n" +
    "        <ul role=\"group\">\n" +
    "            <b2b-member ng-repeat='member in value.childArray' member='member' time-delay='{{timeDelay}}' group-it='groupIt'></b2b-member>\n" +
    "        </ul>\n" +
    "    </li>\n" +
    "</ul>");
}]);

angular.module("b2bTemplate/treeNav/treeMember.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/treeNav/treeMember.html",
    "<li role=\"treeitem\" aria-expanded=\"{{(member.active?true:false)}}\" aria-label=\"{{member.tooltipContent ? member.tooltipContent : member.name}}\" aria-describedby=\"description_{{$id}}\" ng-class=\"{'bg':member.selected}\"  b2b-tree-link>\n" +
    "    <a tabindex=\"-1\" title=\"{{member.tooltipContent ? member.tooltipContent : member.name}}\" href=\"javascript:void(0)\" ng-class=\"{'active':member.active,'b2b-locked-node':member.locked}\">	   <span class=\"b2b-tree-node-name\">{{member.name}}</span>\n" +
    "        <span class=\"{{!member.child?'end':''}} b2b-tree-node-icon\">\n" +
    "            <i class=\"b2b-tree-expandCollapse-icon\" ng-class=\"{'icon-expanded':member.active}\"></i>\n" +
    "        </span>\n" +
    "         <div id=\"description_{{$id}}\" class=\"offscreen-text\">\n" +
    "        	{{member.descriptionText}}\n" +
    "        </div>\n" +
    "        <div class=\"b2b-tree-tooltip\" ng-if=\"member.showTooltip\">\n" +
    "        	<span class=\"b2b-tree-arrow-left\"></span>\n" +
    "        	<div class=\"b2b-tree-tooltip-content\">\n" +
    "        		{{member.tooltipContent}}\n" +
    "        	</div>	\n" +
    "        </div>\n" +
    "    </a>\n" +
    "</li>");
}]);

angular.module("b2bTemplate/treeNav/ungroupedTree.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/treeNav/ungroupedTree.html",
    "<ul role=\"{{setRole}}\"><b2b-member ng-repeat='member in collection' member='member' group-it='groupIt'></b2b-member></ul>");
}]);

angular.module("b2bTemplate/treeNodeCheckbox/groupedTree.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/treeNodeCheckbox/groupedTree.html",
    "<ul role=\"group\">\n" +
    "    <li aria-expanded=\"{{(member.active?true:false)}}\" role=\"treeitem\" aria-label=\"{{key}}\" ng-repeat='(key,value) in collection | groupBy : \"grpChild\"' b2b-tree-node-link><a class=\"grp\" ng-class=\"{'active': value.showGroup == true}\" tabindex=\"-1\" href='javascript:void(0);'>\n" +
    "        <span class=\"ng-hide\">\n" +
    "            <label class=\"checkbox\">\n" +
    "                <input type=\"checkbox\" tabindex=\"-1\" class=\"treeCheckBox grpTreeCheckbox\" style=\"margin-top:2px;\"/><i class=\"skin\"></i><span> {{(key)?key:''}}</span>\n" +
    "            </label>\n" +
    "        </span>\n" +
    "        <span>\n" +
    "            {{(key)?key:''}}    \n" +
    "        </span>\n" +
    "        <span class=\"nodeIcon\"><i class=\"expandCollapseIcon\" ng-class=\"{'icon-expanded': value.showGroup == true, 'icon-collapsed': value.showGroup == undefined || value.showGroup == false }\"></i></span></a>\n" +
    "        <ul role=\"group\">\n" +
    "            <b2b-tree-member ng-repeat='member in value.childArray' member='member' group-it='groupIt'></b2b-tree-member>\n" +
    "        </ul>\n" +
    "    </li>\n" +
    "</ul>");
}]);

angular.module("b2bTemplate/treeNodeCheckbox/treeMember.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/treeNodeCheckbox/treeMember.html",
    "<li role=\"treeitem\" aria-expanded=\"{{(member.active?true:false)}}\" aria-label=\"{{member.name}}\" ng-class=\"{'bg':member.selected}\"  b2b-tree-node-link>\n" +
    "    <a tabindex=\"-1\" title=\"{{member.name}}\" href=\"javascript:void(0)\" ng-class=\"{'active':member.active}\">\n" +
    "    	<span ng-show=\"member.displayCheckbox\">\n" +
    "   			<label class=\"checkbox\">\n" +
    "                <input type=\"checkbox\" tabindex=\"-1\" ng-model=\"member.isSelected\" ng-class=\"{'treeCheckBox': (member.displayCheckbox !== undefined)}\" style=\"margin-top:2px;\"/><i class=\"skin\"></i><span> {{member.name}}</span>\n" +
    "            </label>\n" +
    "        </span>\n" +
    "    	<span ng-show=\"!member.displayCheckbox\">\n" +
    "    		{{member.name}}	\n" +
    "    	</span>\n" +
    "        <span class=\"nodeIcon {{!member.child?'end':''}}\">\n" +
    "            <i class=\"expandCollapseIcon\" ng-class=\"{'icon-expanded':member.active}\"></i>\n" +
    "        </span>\n" +
    "    </a>\n" +
    "</li>");
}]);

angular.module("b2bTemplate/treeNodeCheckbox/ungroupedTree.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/treeNodeCheckbox/ungroupedTree.html",
    "<ul role=\"{{setRole}}\"><b2b-tree-member ng-repeat='member in collection' member='member' group-it='groupIt'></b2b-tree-member></ul>");
}]);

angular.module("b2bTemplate/usageBar/usageBar.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("b2bTemplate/usageBar/usageBar.html",
    "<div class=\"b2b-progress-bars {{barTypeClass}}\" ng-style=\"item\" ng-repeat=\"item in barsArray\" ng-if=\"item.showItem\"></div>\n" +
    "<span ng-transclude></span>");
}]);
