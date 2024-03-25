let url = "https://raw.githubusercontent.com/pieProject-Co-buying/pie/develop/src/main/resources/static/json/servicePlan.json";
		$
				.ajax({
					url : url,
					type : "get",
					dataType : "json",
					success : function(data) {
						let category = $(".sh-categories .dropdown-item");

						category.click(function() {
							$("#h-input").val($(this).attr("data-category"));
							$(this).parent().siblings(".dropdown-toggle").text(
									$(this).text());
							let select1 = $("#h-input").val();

							if (select1 != '') {
								$(".sh-categories2").siblings(
										".dropdown-toggle").removeClass(
										"disabled");
							}

							$(".sh-categories2").siblings(".dropdown-toggle")
									.text("지원서비스");
							$("#brand").val('');
							$(".sh-categories2").empty(); // 선택 시 이전 내용 지우기

							if (select1 in data) {
								let subcategories = data[select1];

								for ( let subcategory in subcategories) {
									$(".sh-categories2").append(
											'<a class="dropdown-item" data-subcategory="' + subcategory + '">'
													+ subcategory + '</a>');
								}
							}
						});

						// sh-categories2 항목이 선택되었을 때
						$(".sh-categories2")
								.on(
										"click",
										".dropdown-item",
										function() {
											let select2 = $(this).data(
													"subcategory");
											$(this).parent().siblings(
													".dropdown-toggle").text(
													select2);
											$("#brand").val(select2);
											if (select2 != '') {
												$(".sh-categories3")
														.siblings(
																".dropdown-toggle")
														.removeClass("disabled");
											}

											if (select2) {
												$(".sh-categories3").siblings(
														".dropdown-toggle")
														.text("플랜선택");
												$("#productName").val('');

												$(".sh-categories3").empty();

												let subcategoryData = data[$(
														"#h-input").val()][select2];

												for (let i = 0; i < subcategoryData.length; i++) {
													$(".sh-categories3")
															.append(
																	'<a class="dropdown-item" data-plan="' + subcategoryData[i].plan + '">'
																			+ subcategoryData[i].plan
																			+ '</a>');
												}
											}
										});

						$(".sh-categories3")
								.on(
										"click",
										".dropdown-item",
										function() {
											let idx = $(this).index();
											let select3 = $(this).data("plan");
											$(this).parent().siblings(
													".dropdown-toggle").text(
													select3);
											$("#productName").val(select3);

											$("#personnelMax")
													.val(
															data[$("#h-input")
																	.val()][$(
																	"#brand")
																	.val()][idx].person);
											$("#price_total")
													.val(
															data[$("#h-input")
																	.val()][$(
																	"#brand")
																	.val()][idx].price);
											$("#price_per")
													.val(
															Math
																	.ceil($(
																			"#price_total")
																			.val()
																			/ $(
																					"#personnelMax")
																					.val()));
										})
					}
				});