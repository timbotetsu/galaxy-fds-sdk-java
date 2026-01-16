package com.xiaomi.infra.galaxy.fds.auth.signature;

import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.infra.galaxy.fds.SubResource;
import com.xiaomi.infra.galaxy.fds.exception.GalaxyFDSException;
import com.xiaomi.infra.galaxy.fds.model.HttpMethod;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Signer {

  private static final Log LOG = LogFactory.getLog(Signer.class);

  private static final Set<String> SUB_RESOURCE_SET = new HashSet<String>();
  private static final String XIAOMI_DATE = XiaomiHeader.DATE.getName();
  private static final String MULTIPART_FORM_DATE = "multipart/form-data";

  static {
    for (SubResource r : SubResource.values()) {
      SUB_RESOURCE_SET.add(r.getName());
    }
  }

  /**
   * Sign the specified http request.
   *
   * @param httpMethod        The http request method({@link HttpMethod})
   * @param uri               The uri string
   * @param httpHeaders       The http request headers
   * @param secretAccessKeyId The user's secret access key
   * @param algorithm         The sign algorithm({@link SignAlgorithm})
   * @return Byte buffer of the signed result
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   */
  public static byte[] sign(HttpMethod httpMethod, URI uri,
    Map<String, List<String>> httpHeaders, String secretAccessKeyId,
    SignAlgorithm algorithm)
    throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {

    Objects.requireNonNull(httpMethod);
    Objects.requireNonNull(uri);
    Objects.requireNonNull(secretAccessKeyId);
    Objects.requireNonNull(algorithm);

    String stringToSign = constructStringToSign(httpMethod, uri, httpHeaders);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Sign for request: " + httpMethod + " " + uri + ", stringToSign=" + stringToSign);
    }

    Mac mac = Mac.getInstance(algorithm.name());
    mac.init(new SecretKeySpec(secretAccessKeyId.getBytes(), algorithm.name()));
    return mac.doFinal(stringToSign.getBytes("UTF-8"));
  }

  /**
   * A handy version of {@link #sign(HttpMethod, URI, LinkedListMultimap, String, SignAlgorithm)},
   * generates base64 encoded sign result.
   */
  public static String signToBase64(HttpMethod httpMethod, URI uri, Map<String, List<String>> httpHeaders,
    String secretAccessKeyId, SignAlgorithm algorithm)
    throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
    return Base64Util.encode(sign(httpMethod, uri, httpHeaders, secretAccessKeyId, algorithm));
  }

  public static URI generatePresignedUri(String baseUri, String bucketName, String objectName,
    List<String> subResources, Date expiration, HttpMethod httpMethod, String accessId,
    String accessSecret, SignAlgorithm signAlgorithm) throws GalaxyFDSException {
    return generatePresignedUri(baseUri, bucketName, objectName, subResources, expiration,
      httpMethod, accessId, accessSecret, signAlgorithm, null);
  }

  public static URI generatePresignedUri(String baseUri, String bucketName, String objectName,
    List<String> subResources, Date expiration, HttpMethod httpMethod, String accessId,
    String accessSecret, SignAlgorithm signAlgorithm, Map<String, List<String>> httpHeaders)
    throws GalaxyFDSException {
    URI encodedUri = null;

    try {
      if (objectName != null && !objectName.isEmpty()) {
        objectName = "/" + objectName;
      } else {
        objectName = "";
      }
      URI uri = new URI(baseUri);
      if (subResources == null || subResources.isEmpty()) {
        encodedUri = new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(),
          "/" + bucketName + objectName,
          Common.GALAXY_ACCESS_KEY_ID + "=" + accessId + "&" + Common.EXPIRES + "="
            + expiration.getTime(), null);
      } else {
        encodedUri = new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(),
          "/" + bucketName + objectName,
          StringUtils.join(subResources, "&") + "&" + Common.GALAXY_ACCESS_KEY_ID + "=" + accessId
            + "&" + Common.EXPIRES + "=" + expiration.getTime(), null);
      }

      String signature =
        Signer.signToBase64(httpMethod, encodedUri, httpHeaders, accessSecret, signAlgorithm);
      return new URI(encodedUri.toString() + "&" + Common.SIGNATURE + "=" + signature);
    } catch (URISyntaxException e) {
      LOG.error("Invalid URI syntax", e);
      throw new GalaxyFDSException("Invalid URI syntax", e);
    } catch (InvalidKeyException e) {
      LOG.error("Invalid secret key spec", e);
      throw new GalaxyFDSException("Invalid secret key spec", e);
    } catch (NoSuchAlgorithmException e) {
      LOG.error("Unsupported signature algorithm:" + signAlgorithm, e);
      throw new GalaxyFDSException("Unsupported signature algorithm:" + signAlgorithm, e);
    } catch (UnsupportedEncodingException e) {
      LOG.error("Failed to encode the url:" + encodedUri.toString(), e);
      throw new GalaxyFDSException("Failed to encode the url:" + encodedUri.toString(), e);
    }
  }

  /**
   * @param httpMethod        The http request method
   * @param uri               The uri string
   * @param httpHeaders       The http request headers
   * @param accessKeyId       The user's access key
   * @param secretAccessKeyId The user's secret access key
   * @param algorithm         The sign algorithm
   * @return return the value of Authorization Http header
   * @see #sign(HttpMethod, URI, LinkedListMultimap, String, SignAlgorithm)
   * @see #signToBase64(HttpMethod, URI, LinkedListMultimap, String, SignAlgorithm)
   */
  public static String getAuthorizationHeader(HttpMethod httpMethod, URI uri,
    Map<String, List<String>> httpHeaders, String accessKeyId, String secretAccessKeyId,
    SignAlgorithm algorithm)
    throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
    String signature = signToBase64(httpMethod, uri, httpHeaders, secretAccessKeyId, algorithm);
    return "Galaxy-V2 " + accessKeyId + ":" + signature;
  }

  static String constructStringToSign(HttpMethod httpMethod, URI uri,
    Map<String, List<String>> httpHeaders) {
    StringBuilder builder = new StringBuilder();
    builder.append(httpMethod.name()).append("\n");

    builder.append(checkAndGet(httpHeaders, Common.CONTENT_MD5).get(0)).append("\n");
    builder.append(getContentType(httpHeaders)).append("\n");

    long expires = 0;
    if ((expires = getExpires(uri)) > 0) {
      // For pre-signed URI
      builder.append(expires).append("\n");
    } else {
      String xiaomiDate = checkAndGet(httpHeaders, XIAOMI_DATE).get(0);
      String date = "";
      if ("".equals(xiaomiDate)) {
        date = checkAndGet(httpHeaders, Common.DATE).get(0);
      }
      builder.append(checkAndGet(date)).append("\n");
    }

    builder.append(canonicalizeXiaomiHeaders(httpHeaders));
    builder.append(canonicalizeResource(uri));
    return builder.toString();
  }

  static String canonicalizeXiaomiHeaders(Map<String, List<String>> headers) {
    if (headers == null) {
      return "";
    }

    // 1. Sort the header and merge the values
    Map<String, String> sortedHeaders = new TreeMap<String, String>();
    Set<String> keySet = headers.keySet();
    for (String k : keySet) {
      String key = k.toLowerCase();
      if (!key.startsWith(Common.XIAOMI_HEADER_PREFIX)) {
        continue;
      }

      List<String> entries = headers.get(k);
      sortedHeaders.put(key, String.join(",", entries));
    }

    // 2. TODO(wuzesheng) Unfold multiple lines long header

    // 3. Generate the canonicalized result
    StringBuilder result = new StringBuilder();
    for (Entry<String, String> entry : sortedHeaders.entrySet()) {
      result.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
    }
    return result.toString();
  }

  static String canonicalizeResource(URI uri) {
    StringBuilder result = new StringBuilder();
    result.append(uri.getPath());

    // 1. Parse and sort subresources
    TreeMap<String, String> sortedParams = new TreeMap<>();
    Map<String, List<String>> params = Utils.parseUriParameters(uri);
    params.forEach((key, values) -> {
      if (SUB_RESOURCE_SET.contains(key) && values != null && !values.isEmpty()) {
        sortedParams.put(key, values.getLast());
      }
    });

    // 2. Generate the canonicalized result
    if (!sortedParams.isEmpty()) {
      result.append("?");
      boolean isFirst = true;
      for (Entry<String, String> entry : sortedParams.entrySet()) {
        if (isFirst) {
          isFirst = false;
          result.append(entry.getKey());
        } else {
          result.append("&").append(entry.getKey());
        }

        if (!entry.getValue().isEmpty()) {
          result.append("=").append(entry.getValue());
        }
      }
    }
    return result.toString();
  }

  static String checkAndGet(String name) {
    return name == null ? "" : name;
  }

  static List<String> checkAndGet(Map<String, List<String>> headers, String header) {
    List<String> result = new LinkedList<>();
    if (headers == null) {
      result.add("");
      return result;
    }

    List<String> values = headers.get(header);
    if (values == null || values.isEmpty()) {
      result.add("");
      return result;
    }
    return values;
  }

  static String getContentType(Map<String, List<String>> headers) {
    String result = "";
    if (headers != null) {
      List<String> values = headers.get(Common.CONTENT_TYPE);
      if (values != null && !values.isEmpty()) {
        result = values.get(0);
      }
      if (result.startsWith(MULTIPART_FORM_DATE)) {
        result = MULTIPART_FORM_DATE;
      }
    }
    return result;
  }

  static long getExpires(URI uri) {
    Map<String, List<String>> params = Utils.parseUriParameters(uri);
    List<String> expires = params.get(Common.EXPIRES);
    if (expires != null && !expires.isEmpty()) {
      return Long.parseLong(expires.get(0));
    }
    return 0;
  }
}
