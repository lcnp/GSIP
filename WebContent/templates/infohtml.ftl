<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
        content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<link rel="icon" href="${host}/app/img/favicon.ico" />
<link rel="canonical" href="${model.getContextResourceUri()}"/>
<title>Resource</title>
<link href="${host}/app/css/bootstrapmin.css" rel="stylesheet" />
<link href="${host}/app/css/navbartopfixed.css" rel="stylesheet" />
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">
<style type="text/css">
/* Footer */
.mastfoot {
        color: rgba(255, 255, 255, .5);
        display: -ms-flexbox;
        display: -webkit-box;
        display: flex;
        -ms-flex-pack: center;
        -webkit-box-pack: center;
        justify-content: center;
        color: #fff;
        /*text-shadow: 0 .05rem .1rem rgba(0, 0, 0, .5);*/
        box-shadow: inset 0 0 5rem rgba(0, 0, 0, .1);
        padding: 10px 10px 10px 10px;
}



      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }

/* Sticky footer styles
-------------------------------------------------- */
html {
  position: relative;
  min-height: 100%;
}
body {
  /* Margin bottom by footer height */
  margin-bottom: 60px;
}
.footer {
  position: absolute;
  bottom: 0;
  width: 100%;
  /* Set the fixed height of the footer here */
  height: 60px;
  line-height: 60px; /* Vertically center the text there */
  background-color: #f5f5f5;
}



</style>
<script language="" type="application/ld+json">
${model.encode("JSON-LD")}
</script>

    <!-- Custom styles for this template -->
    <link href="https://getbootstrap.com/docs/4.5/examples/sticky-footer-navbar/sticky-footer-navbar.css" rel="stylesheet">


</head>
<body>
        <header>
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
                <img src="${host}/app/img/${model.getLocText('cgdn_logo_white.png','cgdn_logo_white.png')}"
                        alt="" style="max-height:65px;" /> <a class="navbar-brand" href="#" style="padding-left: 12px;">
                        ${model.getLocText("CGDN: Canadian Geoscience Data Network","RDGC: Réseau de données géoscientifiques canadien")}</a>
                        <a class="btn-nav mr-auto" href="?lang=${model.getLocText('fr','en')}">${model.getLocText("Français","English")}</a>
        </nav>
        </header>

        <main role="main" class="container" style="padding-top: 10px">
                <div class="row">
                        <div class="col-sm-12">
                                <h1>${model.getPreferredLabel("N/A")}</h1>
                                <blockquote class="blockquote container">
                                        <div class="row">
						<div class="col-sm-12 col-md-12">
                                                     <!--   <p class="mb-0">
                                                                <strong>Type: </strong>
                                                                <samp>${model.getTypeLabel()}</samp>
						     </p> -->
                                                        <p class="mb-0">
                                                                <strong>${model.getLocText("Identifier:","Identifiant:")} </strong>
                                                                <samp>
                                                                        <a href="${model.getContextResourceUri()}">${model.getContextResourceUri()}</a>
                                                                </samp>
                                                        </p>
						<!--	<p><small>${model.getLocText("(Unclassified information)","(Information non classée)")}</small></p> -->
                                                </div>
                                                <div class="col-sm-12 col-md-3">
                                                        <a href="${model.getContextResourceUri()}?f=rdf"
                                                                target="_blank"><img class="img-fluid"
                                                                title="${model.getLocText('Display page in RDF/XML format','Afficher la page en format RDF/XML')}"
                                                                alt="${model.getLocText('View in RDF/XML format','Afficher en format RDF/XML')}"
                                                                src="${host}/app/img/rdfxmlicon.png"
                                                                style="max-width: 35px; padding: 10px 5px 0 5px" />
                                                        </a>
                                                        <a
                                                                href="${model.getContextResourceUri()}?f=json"
                                                                target="_blank"><img class="img-fluid"
                                                                title="${model.getLocText('Display page in JSON-LD format','Afficher la page en format JSON-LD')}"
                                                                alt="${model.getLocText('View in JSON-LD format','Afficher en JSON-LD')}"
                                                                src="${host}/app/img/jsonicon.png"
                                                                style="max-width: 35px; padding: 10px 5px 0 5px" /></a>
                                                        <a
                                                                href="${model.getContextResourceUri()}?f=ttl"
                                                                target="_blank"><img class="img-fluid"
                                                                title="${model.getLocText('Display page in TTL format','Afficher la page en format TTL')}"
                                                                alt="${model.getLocText('View in TTL format','Afficher en format TTL')}"
                                                                src="${host}/app/img/ttlicon.png"
                                                                style="max-width: 35px; padding: 10px 5px 0 5px" /></a>

                                                   <!--  not desired icon    <a href="${model.getNonInfoUri()}LOD_Node/CAN_Hydro_LOD_Node"
                                                                target="_blank"> <img class="img_fluid"
                                                                title="${model.getLocText('This node','Ce noeud')}" alt="${model.getLocText('Access this node','Accéder à ce noeud')}"
                                                                src="${host}/app/img/node.png"
								style="max-width: 35px; padding: 10px 5px 0 5px" /></a> -->
                                                </div>
                                        </div>
                                </blockquote>
                                <div class="container">
                                        <div class="row">
                                                <#assign image = model.getPropertyResource("https://schema.org/image")>
                                                <#if image?has_content>
                                                <div class="col-sm-12 col-md-8">
                                                        <#else>
                                                <div class="col-sm-12 col-md-12">
                                                        </#if>
                                                        <!--<small>(unclassified - non classifié)</small>-->
							<!--   <h3>${model.getLocText("Feature Descriptions","Descriptions de l'entité")}</h3> -->
                                                        <!--<i class="material-icons">arrow_right</i><i class="material-icons">arrow_drop_down</i>-->

                                                        <#assign collapsableId = 0>
                                                        <#assign collapsableShow = ''>
                                                        <#assign collapsableShow_arrow = 'arrow_drop_down'>
                                                        <div class="accordion" id="accordionExample">

							   <!-- sort the providers by name -->
							   <#assign providers = []>
							   <#list model.getAllProviders(true) as p1>
							   <#assign providers=providers+[{"name":model.getPreferredLabel(p1,l,"N/A"),"provider":p1}]>
							   </#list>
							   <!-- end sorting providers -->

							   <#--  <#list model.getAllProviders(true) as p> -->
							<#list providers?sort_by("name") as prov>
							   <#assign p = prov.provider>


                                                         <#if collapsableId == 0>
                                                                <#assign collapsableShow = 'show'>
                                                                <#assign collapsableShow_arrow = 'arrow_drop_down'>
                                                         <#else>
                                                                <#assign collapsableShow = ''>
                                                                <#assign collapsableShow_arrow = 'arrow_right'>
                                                         </#if>


                                                         <div class="card">
                                                                <div class="card-header" id="heading_${collapsableId}" > <!-- style="display:flex;justify-content: space-between;"  >  -->
                                                                        <div style="display:flex;align-items:center;justify-content: space-between;">
                                                                <h2 class="mb-0"  >
                                                                        <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse" data-target="#collapse_${collapsableId}" aria-expanded="true" aria-controls="collapse_${collapsableId}">
                                                                        Source : ${model.getJoinedLabels(p, locale, true," | ")}<sub><i class="material-icons"> ${collapsableShow_arrow}</i>
                                                                        </sub></button>
                                                                </h2>
                                                                        <#list model.getDatasetForProvider(p,true,locale) as ds>
                                                                                <a
                                                                                        href="${ds.getUrl()}?lang=${locale}"
                                                                                        title="${ds.getUrl()}"> Dataset: ${ds.getResLabel()}</a> </#list>
									<!-- <a> End div source title and dataset</a> -->
                                                                        </div>  
								<!-- <a> End div card-header</a> -->
                                                                </div>
                                                                <div id="collapse_${collapsableId}" class="collapse ${collapsableShow}" aria-labelledby="heading_${collapsableId}" data-parent="#accordionExample">
                                                                        <div class="card-body">
                                                                        <#assign items =model.getRepresentationByProvider(p,true)>
                                                                       <#assign firstItem = items?first>
                                                                        <#assign myString = model.getJoinedLabels(firstItem, locale, true," | ")>
                                                                        <#assign parts = myString?split(":")>
                                                                        <#assign theLable = parts[1]?trim>
                                                                        <#assign labels = theLable?split(" | ")>
                                                                        <#list labels as part>
                                                                        ${part} <br>
                                                                        </#list>
                                                                        									
				                           <!-- <table width="100%" style="border_collpase:collapse" >
								   <tr> -->  <a> <b> Data formats </b> </a> 

							                <div class="representation"  style="display:flex;gap: 10px;">

								   <!-- sort the representations -->
								   <#assign representations = [] >
								  <#list model.getRepresentationByProvider(p,true) as rep>
								  <#assign representations=representations + [{"name":model.getJoinedLabels(rep, locale, true, " | "),"representation":rep}]>
								  </#list>
						  	   <#list representations?sort_by("name") as rp>
								  <#assign r = rp.representation>
 

								  <#--  <#list model.getRepresentationByProvider(p,true) as r> -->

								   <!--	<td style="padding: 10px" > -->	<!--	<tr><td style="vertical-align:top"> -->
												<!--	<div style="display:flex" width="100%" > -->

                                                                        <#assign links = []>
                                                                        <#list model.getUrls(r,true) as url>
                                                                        <#assign link><a href="${url.getUrl()}"><#switch url.getLabel()>
												<#case "application/rdf+xml"><img class="img-fluid" title="${model.getLocText('Display in RDF/XML format','Afficher en format RDF/XML')}" alt="${model.getLocText('Display in RDF/XML','Afficher en format RDF/XML')}" src="${host}/app/img/rdfxmlicon.png" style="max-width: 60px; width: 90px;padding: 10px 5px 0 5px"/><#break>
												<#case "text/xml"><img class="img-fluid" title="${model.getLocText('Display in XML format','Afficher en format XML')}" alt="${model.getLocText('Display in XML format','Afficher en format XML')}" src="${host}/app/img/xmlicon.png" style="max-width: 60px;width: 12px; padding: 10px 5px 0 5px"/><#break>
												<#case "application/gml+xml;subtype=erml"><img class="img-fluid" title="${model.getLocText('Display in ERML format','Afficher en format ERML')}" alt="${model.getLocText('Display in ERML format','Afficher en format ERML')}" src="${host}/app/img/ERML_logo.png" style="max-width: 60px; width: 90px;padding: 10px 5px 0 5px"/><#break>
												<#case "application/gml+xml"><img class="img-fluid" title="${model.getLocText('Display in GML/XML format','Afficher en format GML/XML')}" alt="${model.getLocText('Display in GML/XML format','Afficher en format GML/XML')}" src="${host}/app/img/gmlicon.png" style="max-width: 60px;width: 90px; padding: 10px 5px 0 5px"/><#break>
												<#case "application/ld+json"><img class="img-fluid" title="${model.getLocText('Display in JSON format','Afficher en format JSON')}" alt="${model.getLocText('Display in JSON format','Afficher en format JSON')}" src="${host}/app/img/jsonicon.png" style="max-width: 60px;width: 90px; padding: 10px 5px 0 5px"/><#break>
												<#case "application/x-turtle"><img class="img-fluid" title="${model.getLocText('Display in TTL (Turtle) format','Afficher en format TTL (Turtle)')}" alt="${model.getLocText('Display in TTL (Turtle) format','Afficher en format TTL (Turtle)')}" src="${host}/app/img/ttlicon.png" style="max-width: 70px; width: 60px; padding: 10px 5px 0 5px"/><#break>
												<#case "text/html"><img class="img-fluid" title="${model.getLocText('Display web page''Afficher la page Web')}" alt="${model.getLocText('Display web page''Afficher la page Web')}" src="${host}/app/img/HTML_logo.png" style="max-width: 60px; width: 90px;padding: 10px 5px 0 5px" /><#break>
												<#case "text/plain"><img class="img-fluid" title="${model.getLocText('Display in plain text format''Afficher en format texte')}" alt="${model.getLocText('Display in plain text format''Afficher en format texte')}t" src="${host}/app/img/txticon.png" style="max-width: 70px; width: 60px;padding: 10px 5px 0 5px"/><#break>
												<#case "image/jpeg"><img class="img-fluid" title="${model.getLocText('Display in jpeg','Afficher en jpeg')}" alt="${model.getLocText('Display in jpeg','Afficher en jpeg')}" src="${url.getUrl()}" style="max-width: 60px;width: 90px; padding: 10px 5px 0 5px;border: 1px solid black;"/><#break>
												<#case "image/png"><img class="img-fluid" title="${model.getLocText('Display in png','Afficher en png')}" alt="${model.getLocText('Display in png','Afficher en png')}" src="${host}/app/img/PNG_logo.png"  style="max-width: 60px;width: 90px; padding: 10px 5px 0 5px;transform: scale(1);"/> <!--border: 1px solid black;"/> --> <#break>
												<#case "application/vnd.geo+json"><img class="img-fluid" title="${model.getLocText('Display in GeoJSON format''Afficher en format GeoJSON')}" alt="${model.getLocText('Display in GeoJSON format''Afficher en format GeoJSON')}" src="${host}/app/img/geojsonicon.png" style="max-width: 60px; width: 90px;padding: 10px 5px 0 5px"/><#break>
												<#default><img class="img-fluid" title="${model.getLocText('Display in','Afficher en format ')} ${url.getLabel()} ${model.getLocText('format','')}" alt="${model.getLocText('Display in','Afficher en format ')} ${url.getLabel()} ${model.getLocText('format','')}" src="${host}/app/img/CSV_logo.png" style="max-width: 60px; width: 90px;padding: 10px 5px 0 5px"/></#switch></a>
                                                                        </#assign>
                                                                        <#assign links = links + [link]>
                                                                        </#list>

                                                                        ${links?join(" ")}
								<!--	${model.getJoinedLabels(r, locale, true," | ")} -->

                                                                        <!-- </td></tr> -->
                                                                        </#list> <!-- </div> < end of division for the label and formats clickable icones -->
                                                                                <#-- representation-->
                                                                               <!--	</td>  -->
									       <!-- here was the old representation div close -->
									       <!-- <a> end class representation </a> --> 
							   	</div> <#-- class representation -->
								 <!-- <a> end class card body  </a> -->

								</div> <#-- class card body -->
							 <!-- <a> end class collapse_id  </a> -->
							</div><#-- class collapse_id --> 
							<!-- <a> end card division </a> -->
							</div> 
                                                        
                                                        <#assign collapsableId = collapsableId + 1>
							
                                                         <!-- <a> end collapse class starting the list of sources  </a> -->
							
							 </#list>  <!-- should be end of list of providers -->
							 </div>   <!--  end of sm12 div and it is moved to after end provider list  -->
                                                         </div> <!-- added to end div row after the end of providers list -->
                                                         
							</div>  <!-- should be end of the container div --> 

                                                        
                                                        <h3>${model.getLocText("Related Features","Entités reliées")}</h3>
                                                        <ul class="nav nav-tabs" role="tablist">
                                                                <li class="nav-item"><a class="nav-link active"
                                                                        href="#g_type" role="tab" data-toggle="tab">${model.getLocText("Grouped by relations","Groupées par relations")}</a></li>
                                                                <li class="nav-item"><a class="nav-link" href="#g_res"
                                                                        role="tab" data-toggle="tab">${model.getLocText("Grouped by features","Groupées par entités")}</a></li>
                                                        </ul>
                                                        <div class="tab-content">
                                                                <div role="tabpanel" class="tab-pane active" id="g_type">
                                                                        <ul>
                                                                        <#assign grp = model.getRelevantLinkByProperty()>
                                                                        <#list grp?keys as p>
                                                                                <li><strong>${p}:</strong>
                                                                                </br>
                                                                                <ul>
                                                                                <#list grp[p] as link>
                                                                                <li>
                                                                                <a
                                                                                        href="${link.getUrl()}?lang=${locale}"
                                                                                        title="${link.getUrl()}">${link.getResLabel()}</a>
                                                                                 </li>        
                                                                                </#list>
                                                                                </ul>

                                                                                </li>
                                                                        </#list>
                                                                        </ul>
                                                                </div>
                                                                <div role="tabpanel" class="tab-pane" id="g_res">
                                                                        <ul>
                                                                        <#assign res = model.getRelevantLinkByResource()>
                                                                        <#list res?keys as r>
                                                                                <li>
                                                                                <#list res[r] as l>
                                                                                 <a
                                                                                        href="${l.getUrl()}?lang=${locale}"
                                                                                        title="${l.getUrl()}">${l.getResLabel()}</a> : <strong>${l.getLabel()}</strong>
                                                                                </#list>

                                                                                </li>
                                                                        </#list>


                                                                        </ul>
                                                                </div>
                                                        </div>
                                                </div>
                                                <#-- I'll keep this feature, so if a /id/ has a schema:image, it will do something with it -->
                                                <#if image?has_content>
                                                <div class="col-sm-12 col-md-4">
                                                        <a href="${image[0]}" target="_blank">
                                                                <img src="${image[0]}" class="img-thumbnail img-fluid"/>
                                                        </a>
                                                </div>
                                                </#if>
                                        </div>
                                </div>
                        </div>
                </div>
              <!--  <a href="https://www.nrcan.gc.ca/terms-and-conditions/10847" target="_blank"><small>[Terms and conditions of use]</small></a>  <a href="https://www.rncan.gc.ca/avis/10848" target="_blank"><small>[Conditions régissant l'utilisation]</small></a> -->
                <a href="${host}/app/CGDN-Terms-ConditionsNoComments2025-03-11.htm"   target="_blank"><small>[Terms and conditions of use]</small></a>  <a href="${host}/app/CGDN-Terms-ConditionsNoComments2025-03-11.htm" target="_blank"><small>[Conditions régissant l'utilisation]</small></a>

        </main>

        <!---<footer class="mastfoot mt-auto">
                <div class="inner">
                        <img class="img-fluid" alt="Government of Canada logo"
                                src="${host}/app/img/GOCcolouren.png"
                                style="max-height: 35px;" />
                </div>
        </footer>--->
        <br/>
    <footer class="mastfoot fixed-bottom" >
      <div class="container d-flex justify-content-evenly align-items-center" >
        <span class="text-muted"  style="border: 2px solid black; "> <img class="img-fluid" alt="NGSC logo"
         src="${host}/app/img/NGSClogo.png"
         style="max-height: 60px; " />
                                
        </span>
        
        <span class="text-muted" style="padding: 10px;"><img class="img-fluid" alt="Government of Canada logo"
                                src="${host}/app/img/CdnFlagBanner.svg"
                                style="max-height: 40px; " />
        </span>
        
      </div>
    </footer>
    


        <script src="https://code.jquery.com/jquery-3.2.1.min.js"
                crossorigin="anonymous" type="text/javascript">

        </script>
        <script src="${host}/app/js/poppermin.js"
                type="text/javascript">

        </script>
        <script src="${host}/app/js/bootstrapmin.js"
                type="text/javascript">

        </script>
        <script src="${host}/app/js/ieworkaround.js"
                type="text/javascript">

        </script>

        <script type="text/javascript">
                $('.collapse').on('hide.bs.collapse', function () {
                        var getallcardheadericons = '.card-header ' + ' h2' + ' button' + ' .material-icons' ;
                        $(getallcardheadericons).text('arrow_right');

                });
                $('.collapse').on('show.bs.collapse', function () {
                        var getallcardheadericons = '.card-header ' + ' h2' + ' button' + ' .material-icons' ;
                        $(getallcardheadericons).text('arrow_right');
                });

                $('.collapse').on('shown.bs.collapse', function () {
                        var parts = this.id.split('_');
                        var idnumber = parts[parts.length - 1];
                        var getcurrentcollapse = '#collapse_' +  idnumber;
                        var getcurrentcardheadericon = '#heading_' + idnumber + ' h2' + ' button' + ' .material-icons' ;
                        $(getcurrentcardheadericon).text('arrow_drop_down');
                });

        </script>
</body>

</html>

