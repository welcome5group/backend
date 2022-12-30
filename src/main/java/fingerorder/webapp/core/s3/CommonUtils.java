package fingerorder.webapp.core.s3;

import java.util.UUID;

public class CommonUtils {

	private static final String FILE_EXTENSION_SEPARATOR = ".";
	private static final String CATEGORY_PREFIX = "/";

	public static String buildFileName(String category, String originalFileName) {
		int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);	// 파일확장자 구분선
		String fileExtension = originalFileName.substring(fileExtensionIndex);	// 파일확장자

		UUID uuid = UUID.randomUUID();

		return category + CATEGORY_PREFIX + uuid + fileExtension;
	}

}
