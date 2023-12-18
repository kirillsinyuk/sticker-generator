package com.kvsinyuk.stickergenerator.applicaiton.service.impl

import com.kvsinyuk.stickergenerator.applicaiton.service.AddTextService
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.GraphicsEnvironment
import java.awt.RenderingHints
import java.awt.Shape
import java.awt.image.BufferedImage

@Service
class AddTextServiceImpl : AddTextService {

    private val MAX_FONT_SIZE = 48

    private val BOTTOM_MARGIN = 10
    private val TOP_MARGIN = 5
    private val SIDE_MARGIN = 10

    private val TEXT_COLOR = Color.WHITE
    private val OUTLINE_COLOR = Color.BLACK
    private val OUTLINE_STROKE = BasicStroke(2F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)

    init {
        val input = ClassPathResource("impact.ttf")
            .inputStream
        GraphicsEnvironment.getLocalGraphicsEnvironment()
            .registerFont(Font.createFont(Font.TRUETYPE_FONT, input))
    }

    override fun addText(image: BufferedImage, text: String, isTop: Boolean): BufferedImage {
        text.takeIf { it.isNotBlank() }
            ?.run { drawStringCentered(image.createGraphics(), text, image, isTop) }
        return image
    }

    private fun drawStringCentered(graphics: Graphics2D, text: String, image: BufferedImage, isTop: Boolean) {
        var height: Int
        var fontSize: Int = MAX_FONT_SIZE
        val maxCaptionHeight = image.height / 5
        val maxLineWidth: Int = image.width - SIDE_MARGIN * 2
        var formattedString: String
        do {
            graphics.font = Font("impact", Font.BOLD, fontSize)

            // first inject newlines into the text to wrap properly
            val sb = StringBuilder()
            var left = 0
            var right = text.length - 1
            while (left < right) {
                var substring = text.substring(left, right + 1)
                var stringBounds = graphics.fontMetrics.getStringBounds(substring, graphics)
                while (stringBounds.width > maxLineWidth) {
                    // look for a space to break the line
                    var spaceFound = false
                    for (i in right downTo left + 1) {
                        if (text[i] == ' ') {
                            right = i - 1
                            spaceFound = true
                            break
                        }
                    }
                    substring = text.substring(left, right + 1)
                    stringBounds = graphics.fontMetrics.getStringBounds(substring, graphics)

                    // If we're down to a single word and we are still too wide, the font is just too big.
                    if (!spaceFound && stringBounds.width > maxLineWidth) {
                        break
                    }
                }
                sb.append(substring).append("\n")
                left = right + 2
                right = text.length - 1
            }
            formattedString = sb.toString()

            // now determine if this font size is too big for the allowed height
            height = 0
            for (line in formattedString.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                val stringBounds = graphics.fontMetrics.getStringBounds(line, graphics)
                height += stringBounds.height.toInt()
            }
            fontSize--
        } while (height > maxCaptionHeight)

        // draw the string one line at a time
        var y: Int = if (isTop) {
            TOP_MARGIN + graphics.fontMetrics.height
        } else {
            image.height - height - BOTTOM_MARGIN + graphics.fontMetrics.height
        }
        for (line in formattedString.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            val stringBounds = graphics.fontMetrics.getStringBounds(line, graphics)
            graphics.color = TEXT_COLOR
            paintTextWithOutline(graphics, line, (image.width - stringBounds.width.toInt()) / 2, y)
            y += graphics.fontMetrics.height
        }
        graphics.dispose()
    }

    private fun paintTextWithOutline(graphics: Graphics2D, text: String, x: Int, y: Int) {
        // original settings
        val originalColor = graphics.color
        val originalStroke = graphics.stroke
        val originalHints = graphics.renderingHints

        // create a glyph vector from text
        val textShape: Shape = graphics.font.createGlyphVector(graphics.fontRenderContext, text)
            .getOutline(x.toFloat(), y.toFloat())

        graphics.apply {
            this.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            this.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
            color = OUTLINE_COLOR
            stroke = OUTLINE_STROKE
        }
        graphics.draw(textShape) // draw outline
        graphics.apply { color = originalColor }
        graphics.fill(textShape) // fill text shape

        // reset to original settings after painting
        graphics.apply {
            stroke = originalStroke
            this.setRenderingHints(originalHints)
        }
    }
}