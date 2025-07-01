package utils

import org.eclipse.microprofile.config.ConfigProvider

internal object TestUtils {

  fun getConfigurationValue(
    property: String
  ) : String = ConfigProvider.getConfig().getValue(property, String::class.java)

}
