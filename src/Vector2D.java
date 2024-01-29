public record Vector2D(double x, double y) {
    public Vector2D add(Vector2D that) {
        return new Vector2D(this.x + that.x, this.y + that.y);
    }

    public double dotProduct(Vector2D that) {
        return this.x * that.x + this.y * that.y;
    }

    public double distance(Vector2D that) {
        double distX = (that.x - this.x);
        double distY = (that.y - this.y);
        return Math.sqrt(distX * distX + distY * distY);
    }
}
