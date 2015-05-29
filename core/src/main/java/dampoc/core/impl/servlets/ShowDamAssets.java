package dampoc.core.impl.servlets;

import com.day.cq.dam.api.Asset;
import dampoc.core.FetchDamAssets;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.ComponentContext;
import java.io.IOException;
import java.util.Dictionary;
import java.util.List;
import java.util.ListIterator;

/**
 * Created with IntelliJ IDEA.
 * User: Rima
 * Date: 5/29/15
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
@SlingServlet(
        paths={"/bin/getDam"},
        methods = "GET",
        metatype=true,
        extensions = {"json"}
)
@Properties({
        @Property(name="service.description",value="Show Dam assets", propertyPrivate=false),
        @Property(name="DAM-PATH", description="Dam path to search the assets in", value="/content/dam/poc")
})
public class ShowDamAssets extends SlingSafeMethodsServlet {
    public static final String DAM_PATH="DAM-PATH";
    private String damPath;

    protected void activate(ComponentContext componentContext){
        configure(componentContext.getProperties());
    }
    protected void configure(Dictionary<?, ?> properties) {
        this.damPath=properties.get(DAM_PATH).toString();
    }

    @Reference
    private FetchDamAssets fetchDamAssets;

    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        JSONObject damAssetJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{

            List<Asset> damAssets = fetchDamAssets.getDamAssetList(request.getResourceResolver(), damPath);
            response.getWriter().write("");

            ListIterator<Asset> li = damAssets.listIterator();
            while(li.hasNext()){
                JSONObject assetDetailsJson = new JSONObject();
                Asset currentAsset = li.next();
                assetDetailsJson.put("name", currentAsset.getName());
                assetDetailsJson.put("path", currentAsset.getPath());
                jsonArray.put(assetDetailsJson);
            }

            damAssetJson.put("damAssets", jsonArray);
            response.getWriter().write(damAssetJson.toString());

        } catch(Exception e){
            response.getWriter().write("error fetching paths");
        }

    }


}
