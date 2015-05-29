package dampoc.core;

import com.day.cq.dam.api.Asset;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Rima
 * Date: 5/29/15
 * Time: 9:59 AM
 * To change this template use File | Settings | File Templates.
 */
public interface FetchDamAssets {
    public List<Asset> getDamAssetList(ResourceResolver resourceResolver, String damPath);
}
